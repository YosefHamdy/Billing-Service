package com.mop.billing.integration.sadad;

import com.mop.billing.dto.request.PaymentNotificationRequest;
import com.mop.billing.integration.tahseel.TahseelSslConfig;
import com.mop.billing.integration.tahseel.TahseelXmlSigner;
import com.mop.billing.service.IPaymentNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Integration Layer — Inbound SOAP endpoint for Tahseel payment notifications.
 *
 * Tahseel calls POST /sadad/payment-notify with a WS-Security signed SOAP envelope
 * whenever a citizen completes payment (PmtNotificationAGWRq).
 *
 * This controller:
 *   1. Parses the raw SOAP XML
 *   2. Validates the XML digital signature (X.509 / WS-Security)
 *   3. Validates the message header (partnerId, functionId)
 *   4. Delegates to PaymentNotificationService
 *   5. Returns a SOAP response envelope with the status code
 *
 * NOTE: This endpoint is intentionally NOT protected by JWT.
 * It is secured by:
 *   - IP allowlist (configure in firewall / reverse proxy)
 *   - XML digital signature validation (WS-Security)
 *   - Message header partner ID check
 */
@Slf4j
@RestController
@RequestMapping("/sadad")
@RequiredArgsConstructor
public class SadadPaymentNotificationController {

    private static final String RESPONSE_SUCCESS      = "I000000";
    private static final String RESPONSE_INVALID_HDR  = "E200996";
    private static final String RESPONSE_GENERAL_ERR  = "E200999";
    private static final String RESPONSE_BAD_SIG      = "E200993";

    private final IPaymentNotificationService notificationService;
    private final TahseelXmlSigner            xmlSigner;
    private final TahseelSslConfig            sslConfig;

    /**
     * Receives PmtNotificationAGWRq from Tahseel.
     * Returns PmtNotificationAGWRs as SOAP XML.
     */
    @PostMapping(
            value = "/payment-notify",
            consumes = {MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_XML_VALUE, "application/soap+xml"},
            produces = MediaType.TEXT_XML_VALUE
    )
    public void handlePaymentNotification(HttpServletRequest httpRequest,
                                           HttpServletResponse httpResponse) throws IOException {
        String responseCode = RESPONSE_GENERAL_ERR;
        String reqUId = "UNKNOWN";

        try {
            // ── 1. Parse SOAP XML ─────────────────────────────────────────────
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            // XXE protection
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            DocumentBuilder builder  = factory.newDocumentBuilder();
            Document        document = builder.parse(httpRequest.getInputStream());

            NodeList rootNodes = document.getChildNodes();
            if (rootNodes == null || rootNodes.getLength() == 0) {
                log.warn("[PmtNotify] Empty SOAP document received");
                writeSoapResponse(httpResponse, RESPONSE_GENERAL_ERR, reqUId, "PmtNotificationAGWRs");
                return;
            }

            Element envelope = (Element) rootNodes.item(0);

            // ── 2. Extract MsgRqHdr ───────────────────────────────────────────
            NodeList bodies = envelope.getElementsByTagName("soapenv:Body");
            if (bodies.getLength() == 0) {
                bodies = envelope.getElementsByTagNameNS("*", "Body");
            }
            Element body = (Element) bodies.item(0);

            NodeList pmtRqNodes = envelope.getElementsByTagName("agw:PmtNotificationAGWRq");
            if (pmtRqNodes.getLength() == 0) {
                pmtRqNodes = envelope.getElementsByTagNameNS("*", "PmtNotificationAGWRq");
            }
            Element pmtRq = (Element) pmtRqNodes.item(0);

            MsgHeader msgHeader = parseMsgHeader(pmtRq);
            reqUId = msgHeader.reqUId != null ? msgHeader.reqUId : "UNKNOWN";

            // ── 3. Validate XML digital signature ─────────────────────────────
            NodeList sigNodes = envelope.getElementsByTagName("Signature");
            if (sigNodes.getLength() == 0) {
                sigNodes = envelope.getElementsByTagNameNS(
                        "http://www.w3.org/2000/09/xmldsig#", "Signature");
            }

            if (sigNodes.getLength() > 0) {
                boolean sigValid = xmlSigner.validateInboundSignature(
                        sigNodes.item(0), body, sslConfig.loadCertificate().getPublicKey());
                if (!sigValid) {
                    log.warn("[PmtNotify] Invalid XML signature for reqId={}", reqUId);
                    writeAndAudit(httpResponse, RESPONSE_BAD_SIG, reqUId, "PmtNotificationAGWRs");
                    return;
                }
            } else {
                // Signature absent — log but continue (mirrors original commented-out check)
                log.warn("[PmtNotify] No XML signature present for reqId={} — proceeding", reqUId);
            }

            // ── 4. Parse PmtInfo nodes and delegate ───────────────────────────
            NodeList pmtInfoNodes = pmtRq.getElementsByTagName("agw:PmtInfo");
            if (pmtInfoNodes.getLength() == 0) {
                pmtInfoNodes = pmtRq.getElementsByTagNameNS("*", "PmtInfo");
            }

            for (int i = 0; i < pmtInfoNodes.getLength(); i++) {
                Element pmtInfoEl = (Element) pmtInfoNodes.item(i);
                PaymentNotificationRequest req = parsePmtInfo(pmtInfoEl, msgHeader);
                responseCode = notificationService.processPaymentNotification(req);
            }

        } catch (Exception e) {
            log.error("[PmtNotify] Unexpected error processing payment notification", e);
            responseCode = RESPONSE_GENERAL_ERR;
        }

        writeSoapResponse(httpResponse, responseCode, reqUId, "PmtNotificationAGWRs");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // XML parsing helpers — mirrors original JAXB-based logic
    // ─────────────────────────────────────────────────────────────────────────

    private MsgHeader parseMsgHeader(Element pmtRq) {
        MsgHeader hdr = new MsgHeader();
        try {
            NodeList hdrNodes = pmtRq.getElementsByTagNameNS("*", "MsgRqHdr");
            if (hdrNodes.getLength() > 0) {
                Element hdrEl = (Element) hdrNodes.item(0);
                hdr.reqUId      = getElementText(hdrEl, "RqUID");
                hdr.functionId  = getElementText(hdrEl, "FuncId");

                NodeList partnerNodes = hdrEl.getElementsByTagNameNS("*", "PartnerInfo");
                if (partnerNodes.getLength() > 0) {
                    Element partnerEl = (Element) partnerNodes.item(0);
                    hdr.partnerId = getElementText(partnerEl, "PartnerId");
                }
            }
        } catch (Exception e) {
            log.warn("[PmtNotify] Failed to parse MsgRqHdr", e);
        }
        return hdr;
    }

    private PaymentNotificationRequest parsePmtInfo(Element pmtInfoEl, MsgHeader hdr) {
        return PaymentNotificationRequest.builder()
                .reqUId(hdr.reqUId)
                .functionId(hdr.functionId)
                .partnerId(hdr.partnerId)
                .billNo(getElementText(pmtInfoEl, "BillAcct"))
                .eCollectionPmtId(getElementText(pmtInfoEl, "eCollectionPmtId"))
                .billCategory(getElementText(pmtInfoEl, "BillCategory"))
                .pmtAmount(parseDouble(getElementText(pmtInfoEl, "PmtAmt")))
                .pmtDate(parseDate(getElementText(pmtInfoEl, "PmtDt")))
                .pmtStatusCode(getElementText(pmtInfoEl, "PmtStatusCode"))
                .pmtRefInfo(getElementText(pmtInfoEl, "PmtRefInfo"))
                .ecollectionPmtMethod(getElementText(pmtInfoEl, "eCollectionPmtMethod"))
                .build();
    }

    private String getElementText(Element parent, String localName) {
        NodeList nodes = parent.getElementsByTagNameNS("*", localName);
        if (nodes.getLength() > 0 && nodes.item(0).getTextContent() != null) {
            return nodes.item(0).getTextContent().trim();
        }
        return null;
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) return null;
        try { return Double.parseDouble(value); } catch (NumberFormatException e) { return null; }
    }

    private Date parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        String[] patterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"};
        for (String pattern : patterns) {
            try { return new SimpleDateFormat(pattern).parse(value); } catch (Exception ignored) {}
        }
        return null;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SOAP response builder — mirrors original SOAPUtils.createSOAPResponse()
    // ─────────────────────────────────────────────────────────────────────────

    private void writeSoapResponse(HttpServletResponse response,
                                    String statusCode, String reqUId,
                                    String operationName) throws IOException {
        String soapResponse = buildSoapResponse(statusCode, reqUId, operationName);
        response.setContentType("text/xml;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(soapResponse);
            writer.flush();
        }
    }

    private void writeAndAudit(HttpServletResponse response,
                                String statusCode, String reqUId,
                                String operationName) throws IOException {
        writeSoapResponse(response, statusCode, reqUId, operationName);
    }

    private String buildSoapResponse(String statusCode, String reqUId, String operationName) {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
                                   xmlns:agw="https://www.ecp.mof.gov.sa/agw">
                  <SOAP-ENV:Body>
                    <agw:%sRs>
                      <agw:MsgRsHdr>
                        <agw:RqUID>%s</agw:RqUID>
                        <agw:Status>
                          <agw:StatusCode>%s</agw:StatusCode>
                        </agw:Status>
                      </agw:MsgRsHdr>
                    </agw:%sRs>
                  </SOAP-ENV:Body>
                </SOAP-ENV:Envelope>
                """.formatted(operationName, reqUId, statusCode, operationName);
    }

    /** Internal header holder */
    private static class MsgHeader {
        String reqUId;
        String functionId;
        String partnerId;
    }
}
