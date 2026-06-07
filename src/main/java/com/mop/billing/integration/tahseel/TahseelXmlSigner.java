package com.mop.billing.integration.tahseel;

import com.mop.billing.config.TahseelProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.xml.security.Init;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Handles WS-Security XML digital signing for outbound SOAP messages to Tahseel.
 * Mirrors the original HeaderHandler.handleMessage() signing logic.
 *
 * Used by TahseelGatewayService to sign each SOAP body before dispatch.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TahseelXmlSigner {

    private final TahseelProperties props;

    /**
     * Sign a SOAP body element using the configured PKCS12 private key.
     * The signature is added as a WS-Security header.
     *
     * @param document   parsed SOAP document
     * @param bodyElement the soapenv:Body element to sign
     */
    public void signSoapBody(Document document, Element bodyElement) {
        try {
            TahseelProperties.Keystore ks = props.getKeystore();

            // ── Load private key and certificate ─────────────────────────────
            KeyStore keyStore = KeyStore.getInstance(ks.getInstance());
            try (FileInputStream fis = new FileInputStream(ks.getFile())) {
                keyStore.load(fis, ks.getPassword().toCharArray());
            }
            KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry)
                    keyStore.getEntry(ks.getAlias(),
                            new KeyStore.PasswordProtection(ks.getPassword().toCharArray()));
            PrivateKey   privateKey = entry.getPrivateKey();
            X509Certificate cert    = (X509Certificate) entry.getCertificate();

            // ── Set an Id on the body so Reference can target it ──────────────
            String bodyId = "Body-" + UUID.randomUUID();
            bodyElement.setAttributeNS(
                    "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd",
                    "wsu:Id", bodyId);

            // ── Build XMLSignature ────────────────────────────────────────────
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            Reference ref = fac.newReference(
                    "#" + bodyId,
                    fac.newDigestMethod(DigestMethod.SHA256, null),
                    List.of(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
                    null, null);

            SignedInfo si = fac.newSignedInfo(
                    fac.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
                    fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null),
                    Collections.singletonList(ref));

            KeyInfoFactory kif = fac.getKeyInfoFactory();
            KeyInfo ki = kif.newKeyInfo(
                    List.of(kif.newX509Data(List.of(cert))));

            // ── Sign into the document ────────────────────────────────────────
            DOMSignContext dsc = new DOMSignContext(privateKey, bodyElement.getParentNode());
            javax.xml.crypto.dsig.XMLSignature signature = fac.newXMLSignature(si, ki);
            signature.sign(dsc);

            log.debug("[Tahseel-Signer] SOAP body signed successfully, bodyId={}", bodyId);

        } catch (Exception e) {
            log.error("[Tahseel-Signer] Failed to sign SOAP body", e);
            throw new RuntimeException("Failed to sign Tahseel SOAP request", e);
        }
    }

    /**
     * Validate the XML signature on an inbound Sadad notification.
     * Mirrors PmtNotificationAGW.validateSignature().
     *
     * @param signatureNode the &lt;Signature&gt; element
     * @param bodyNode      the &lt;soapenv:Body&gt; element
     * @param publicKey     the MOF/Tahseel public key from the trusted certificate
     * @return true if signature is valid
     */
    public boolean validateInboundSignature(org.w3c.dom.Node signatureNode,
                                             org.w3c.dom.Node bodyNode,
                                             PublicKey publicKey) {
        try {
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            DOMValidateContext ctx  = new DOMValidateContext(publicKey, signatureNode);
            ctx.setIdAttributeNS(
                    (Element) bodyNode,
                    "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd",
                    "Id");

            javax.xml.crypto.dsig.XMLSignature sig = fac.unmarshalXMLSignature(ctx);
            boolean valid = sig.validate(ctx);
            if (!valid) {
                log.warn("[Tahseel-Signer] Inbound SOAP signature validation FAILED");
            }
            return valid;
        } catch (Exception e) {
            log.error("[Tahseel-Signer] Exception during signature validation", e);
            return false;
        }
    }
}
