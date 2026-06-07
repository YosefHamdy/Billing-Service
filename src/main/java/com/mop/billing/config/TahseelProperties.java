package com.mop.billing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Strongly-typed wrapper for all tahseel.* properties in application.yml.
 */
@Data
@Component
@ConfigurationProperties(prefix = "tahseel")
public class TahseelProperties {

    private String wsdlUrl;
    private String agencyId;
    private String partnerId;
    private String billManageFuncId;
    private String pmtNotificationFuncId;

    private Keystore keystore = new Keystore();
    private Ssl ssl = new Ssl();

    @Data
    public static class Keystore {
        private String file;
        private String instance;
        private String password;
        private String alias;
    }

    @Data
    public static class Ssl {
        private String trustStore;
        private String trustStorePassword;
    }
}
