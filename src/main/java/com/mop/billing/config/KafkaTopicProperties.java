package com.mop.billing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka.topics")
public class KafkaTopicProperties {
    private String billCreated   = "mop.bill.created";
    private String billCancelled = "mop.bill.cancelled";
    private String billPaid      = "mop.bill.paid";
    private String tahseelSent   = "mop.tahseel.sent";
    private String tahseelFailed = "mop.tahseel.failed";
    private String pmtReceived   = "mop.payment.received";
    private String reconReceived = "mop.reconciliation.received";
}
