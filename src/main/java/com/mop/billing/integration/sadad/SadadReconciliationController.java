package com.mop.billing.integration.sadad;

import com.mop.billing.dto.request.ReconciliationRequest;
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

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Integration Layer — Inbound SOAP endpoint for Tahseel agency reconciliation reports.
 *
 * Tahseel calls POST /sadad/reconciliation with a signed SOAP envelope
 * at the end of each settlement period (AgencyReconRptAGWRq).
 *
 * Security model: same as SadadPaymentNotificationController —
 * IP-restricted + XML signature validation. NOT JWT-protected.
 */
@Slf4j
@RestController
@RequestMapping("/sadad")
@RequiredArgsConstructor
public class SadadReconciliationController {

    private static final String CODE_SUCCESS     = "I000000";
    private static final String CODE_INVALID_HDR = "E200996";
    private static final String CODE_BAD_SIG     = "E200993";
    private static final String CODE_ERROR       = "E200999";

    private final IPaymentNotificationService notificationService;
    private final TahseelXmlSigner            xmlSigner;
    private final TahseelSslConfig            sslConfig;

    @PostMapping(
            value = "/reconciliation",
            consumes = {MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_XML_VALUE, "application/soap+xml"},
            produces = MediaType.TEXT_XML_VALUE
    )
    public void handleReconciliation(HttpServletRequest httpRequest,
                                      HttpServletResponse httpResponse) throws IOException {
        String responseCode = CODE_ERROR;
        String reqUId       = "UNKNOWN";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);

            Document document = factory.newDocumentBuilder().parse(httpRequest.getInputStream());
            Element  envelope = (Element) document.getChildNodes().item(0);

            // ── Signature validation ──────────────────────────────────────────
            NodeList bodies   = envelope.getElementsByTagNameNS("*", "Body");
            NodeList sigNodes = envelope.getElementsByTagNameNS(
                    "http://www.w3.org/2000/09/xmldsig#", "Signature");

            if (sigNodes.getLength() > 0 && bodies.getLength() > 0) {
                boolean valid = xmlSigner.validateInboundSignature(
                        sigNodes.item(0), bodies.item(0),
                        sslConfig.loadCertificate().getPublicKey());
                if (!valid) {
                    log.warn("[Reconciliation] Invalid XML signature");
                    writeSoapResponse(httpResponse, CODE_BAD_SIG, reqUId);
                    return;
                }
            }

            // ── Parse reconciliation payload ──────────────────────────────────
            NodeList reconRqNodes = envelope.getElementsByTagNameNS("*", "AgencyReconRptAGWRq");
            if (reconRqNodes.getLength() == 0) {
                log.warn("[Reconciliation] AgencyReconRptAGWRq element not found");
                writeSoapResponse(httpResponse, CODE_ERROR, reqUId);
                return;
            }
            Element reconRq = (Element) reconRqNodes.item(0);

            // Header
            NodeList hdrNodes = reconRq.getElementsByTagNameNS("*", "MsgRqHdr");
            String partnerId  = null;
            String functionId = null;
            if (hdrNodes.getLength() > 0) {
                Element hdrEl = (Element) hdrNodes.item(0);
                reqUId      = text(hdrEl, "RqUID");
                functionId  = text(hdrEl, "FuncId");
                NodeList pi = hdrEl.getElementsByTagNameNS("*", "PartnerInfo");
                if (pi.getLength() > 0) {
                    partnerId = text((Element) pi.item(0), "PartnerId");
                }
            }

            // Recon body
            Date   reconDate  = parseDate(text(reconRq, "ReconDt"));
            String agencyId   = text(reconRq, "AgencyId");
            Integer totalTx   = parseInteger(text(reconRq, "TotalTrxCnt"));
            Double  totalAmt  = parseDouble(text(reconRq, "TotalAmt"));

            ReconciliationRequest req = ReconciliationRequest.builder()
                    .reqUId(reqUId)
                    .functionId(functionId)
                    .partnerId(partnerId)
                    .reconciliationDate(reconDate)
                    .agencyId(agencyId)
                    .totalTransactions(totalTx)
                    .totalAmount(totalAmt)
                    .build();

            responseCode = notificationService.processReconciliation(req);

        } catch (Exception e) {
            log.error("[Reconciliation] Unexpected error", e);
            responseCode = CODE_ERROR;
        }

        writeSoapResponse(httpResponse, responseCode, reqUId);
    }

    private void writeSoapResponse(HttpServletResponse response,
                                    String statusCode, String reqUId) throws IOException {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
                                   xmlns:agw="https://www.ecp.mof.gov.sa/agw">
                  <SOAP-ENV:Body>
                    <agw:AgencyReconRptAGWRs>
                      <agw:MsgRsHdr>
                        <agw:RqUID>%s</agw:RqUID>
                        <agw:Status>
                          <agw:StatusCode>%s</agw:StatusCode>
                        </agw:Status>
                      </agw:MsgRsHdr>
                    </agw:AgencyReconRptAGWRs>
                  </SOAP-ENV:Body>
                </SOAP-ENV:Envelope>
                """.formatted(reqUId, statusCode);

        response.setContentType("text/xml;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter out = response.getWriter()) {
            out.write(xml);
        }
    }

    private String text(Element parent, String localName) {
        NodeList nl = parent.getElementsByTagNameNS("*", localName);
        if (nl.getLength() > 0) {
            String t = nl.item(0).getTextContent();
            return t != null ? t.trim() : null;
        }
        return null;
    }

    private Double parseDouble(String v) {
        if (v == null || v.isBlank()) return null;
        try { return Double.parseDouble(v); } catch (Exception e) { return null; }
    }

    private Integer parseInteger(String v) {
        if (v == null || v.isBlank()) return null;
        try { return Integer.parseInt(v); } catch (Exception e) { return null; }
    }

    private Date parseDate(String v) {
        if (v == null || v.isBlank()) return null;
        for (String p : new String[]{"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"}) {
            try { return new SimpleDateFormat(p).parse(v); } catch (Exception ignored) {}
        }
        return null;
    }
}
