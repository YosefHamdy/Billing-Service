package com.mop.billing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Strongly-typed binding for kafka.consumer.topics.* in application.yml.
 * Exposes topic names as Spring beans so @KafkaListener can reference them via SpEL:
 *   topics = "#{@kafkaConsumerTopics.licenseBillRequest}"
 */
@Data
@Component("kafkaConsumerTopics")
@ConfigurationProperties(prefix = "kafka.consumer.topics")
public class KafkaConsumerTopicProperties {

    /** Published by: License Service — when a license fee bill must be generated */
    private String licenseBillRequest  = "mop.license.bill.request";

    /** Published by: Violations Service — when a fine bill must be generated */
    private String fineBillRequest     = "mop.fine.bill.request";

    /** Published by: Renewal Service — when a renewal fee bill must be generated */
    private String renewalBillRequest  = "mop.renewal.bill.request";

    /** Published by: Export Service — when an export fee bill must be generated */
    private String exportBillRequest   = "mop.export.bill.request";

    /** Published by: License Service / Admin Service — cancel an existing bill */
    private String billCancelRequest   = "mop.bill.cancel.request";
}
