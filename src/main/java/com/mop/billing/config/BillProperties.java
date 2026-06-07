package com.mop.billing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "bill")
public class BillProperties {
    /** How many months after issue date until bill expires in Tahseel */
    private int defaultExpireMonths = 3;
    /** How many days after issue until bill is due */
    private int defaultDueDays = 30;
}
