package com.mop.billing.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer Configuration.
 *
 * Key decisions:
 *
 * 1. MANUAL_IMMEDIATE acknowledgement — messages are only committed after the
 *    listener explicitly calls ack.acknowledge(). A processing failure means
 *    the message is retried (not silently lost).
 *
 * 2. DefaultErrorHandler with FixedBackOff — retries up to 3 times with a
 *    2-second delay before sending to the Dead Letter Topic (DLT).
 *    DLT topic name pattern: <original-topic>.DLT
 *    e.g. mop.license.bill.request → mop.license.bill.request.DLT
 *
 * 3. Separate consumer group per service: "mop-billing-service"
 *    so this service has its own offset tracking independent of other consumers.
 *
 * 4. Concurrency = 3 — three listener threads per topic partition set.
 *    Tune via kafka.consumer.concurrency in application.yml.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${kafka.consumer.concurrency:3}")
    private int concurrency;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,  bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,           "mop-billing-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,   StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,  "earliest");
        // Disable auto-commit — we use manual ACK
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // Fetch tuning
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,   10);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG,    1);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG,  500);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Manual-ACK container factory used by all @KafkaListener methods
     * in BillingEventConsumer.
     *
     * Retry policy: up to 3 attempts, 2 s apart.
     * After 3 failures → message routed to <topic>.DLT for manual inspection.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    manualAckKafkaListenerContainerFactory(KafkaTemplate<String, Object> kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties()
               .setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        // Dead-letter publishing: route failed messages to <topic>.DLT
        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(kafkaTemplate);

        // Retry 3 times with 2 s fixed delay, then DLT
        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(recoverer, new FixedBackOff(2000L, 3));

        // Don't retry on validation / deserialization errors — send straight to DLT
        errorHandler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                com.fasterxml.jackson.core.JsonParseException.class,
                com.fasterxml.jackson.databind.exc.MismatchedInputException.class
        );

        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}
