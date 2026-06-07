package com.mop.billing.integration.tahseel;

import com.mop.billing.config.TahseelProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Mirrors the original SslUtils.createSSlAuthentication().
 * Sets the JVM-wide SSL context so outbound SOAP calls to Tahseel use
 * the correct client certificate and trust store.
 *
 * Call initSsl() once at application startup via @PostConstruct in TahseelGatewayService.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TahseelSslConfig {

    private final TahseelProperties props;

    public void initSsl() {
        try {
            TahseelProperties.Keystore ks = props.getKeystore();
            TahseelProperties.Ssl      ssl = props.getSsl();

            // ── Load client keystore (PKCS12) ────────────────────────────────
            KeyStore keyStore = KeyStore.getInstance("JKS");
            //KeyStore keyStore = KeyStore.getInstance(ks.getInstance());
            try (FileInputStream fis = new FileInputStream(ks.getFile())) {
                keyStore.load(fis, ks.getPassword().toCharArray());
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, ks.getPassword().toCharArray());

            // ── Load trust store ──────────────────────────────────────────────
            KeyStore trustStore = KeyStore.getInstance("JKS");
            try (FileInputStream fis = new FileInputStream(ssl.getTrustStore())) {
                trustStore.load(fis, ssl.getTrustStorePassword().toCharArray());
            }

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            // ── Build and set SSL context ─────────────────────────────────────
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

            // Also set system properties for JAX-WS client
            System.setProperty("javax.net.ssl.keyStore",            ks.getFile());
            System.setProperty("javax.net.ssl.keyStorePassword",    ks.getPassword());
            System.setProperty("javax.net.ssl.keyStoreType",        ks.getInstance());
            System.setProperty("javax.net.ssl.trustStore",          ssl.getTrustStore());
            System.setProperty("javax.net.ssl.trustStorePassword",  ssl.getTrustStorePassword());

            log.info("[Tahseel-SSL] SSL context initialized. Keystore alias: {}", ks.getAlias());

        } catch (Exception e) {
            log.error("[Tahseel-SSL] Failed to initialize SSL context — Tahseel calls will fail!", e);
            throw new IllegalStateException("Tahseel SSL init failed", e);
        }
    }

    /**
     * Load the X.509 certificate from the keystore for signature verification.
     */
    public java.security.cert.X509Certificate loadCertificate() {
        try {
            TahseelProperties.Keystore ks = props.getKeystore();
            KeyStore keyStore = KeyStore.getInstance(ks.getInstance());
            try (FileInputStream fis = new FileInputStream(ks.getFile())) {
                keyStore.load(fis, ks.getPassword().toCharArray());
            }
            KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry)
                    keyStore.getEntry(ks.getAlias(),
                            new KeyStore.PasswordProtection(ks.getPassword().toCharArray()));
            return (java.security.cert.X509Certificate) entry.getCertificate();
        } catch (Exception e) {
            log.error("[Tahseel-SSL] Failed to load certificate", e);
            throw new RuntimeException("Failed to load Tahseel certificate", e);
        }
    }
}
