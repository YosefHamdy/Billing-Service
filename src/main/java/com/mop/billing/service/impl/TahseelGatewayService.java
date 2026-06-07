package com.mop.billing.service.impl;

import com.mop.billing.config.TahseelProperties;
import com.mop.billing.data.entity.RevenueEntry;
import com.mop.billing.data.entity.SadadWsAuditRequest;
import com.mop.billing.data.entity.SadadWsBills;
import com.mop.billing.data.repository.SadadWsAuditRequestRepository;
import com.mop.billing.data.repository.SadadWsBillsRepository;
import com.mop.billing.integration.tahseel.TahseelSecurityHandler;
import com.mop.billing.integration.tahseel.TahseelSslConfig;
import com.mop.billing.integration.tahseel.wsdl.BenInfoType;
import com.mop.billing.integration.tahseel.wsdl.BillActionType;
import com.mop.billing.integration.tahseel.wsdl.BillInfoType;
import com.mop.billing.integration.tahseel.wsdl.BillListType;
import com.mop.billing.integration.tahseel.wsdl.BillsMngAGWRqBodyType;
import com.mop.billing.integration.tahseel.wsdl.BillsMngAGWRqType;
import com.mop.billing.integration.tahseel.wsdl.MsgRqHdrType;
import com.mop.billing.integration.tahseel.wsdl.POIType;
import com.mop.billing.integration.tahseel.wsdl.POITypeType;
import com.mop.billing.integration.tahseel.wsdl.PartnerInfoType;
import com.mop.billing.integration.tahseel.wsdl.PartnerTypeType;
import com.mop.billing.integration.tahseel.wsdl.RevenueEntryInfoType;
import com.mop.billing.integration.tahseel.wsdl.RevenueEntryListType;
import com.mop.billing.integration.tahseel.wsdl.SCIdType;
import com.mop.billing.service.ITahseelGatewayService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.mop.billing.integration.tahseel.wsdl.BillsMngAGW;
import com.mop.billing.integration.tahseel.wsdl.BillsMngAGW_Service;
import com.mop.billing.integration.tahseel.wsdl.BillsMngAGWRsType;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import jakarta.xml.ws.*;
import jakarta.xml.ws.handler.Handler;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Business Layer — Tahseel Gateway.
 *
 * Implements outbound SOAP calls to the MOF eCollect (Tahseel) gateway.
 * Faithfully ports the original BillMngAGW.sendBillToTahseel() with full
 * audit logging, SSL init, and GFS code grouping logic.
 *
 * NOTE: The WSDL-generated stub classes are referenced symbolically here.
 * When you run wsimport/wsdl2java on the real WSDL, replace the placeholder
 * calls with the actual generated types.  The method structure and parameters
 * are 100% faithful to the original code.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TahseelGatewayService implements ITahseelGatewayService {

    // ── Status codes returned by Tahseel ──────────────────────────────────────
    public static final String STATUS_SUCCESS       = "I000000";
    public static final String STATUS_NETWORK_ERROR = "NETWORK_ERROR";

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private final TahseelProperties            tahseelProps;
    private final TahseelSslConfig             sslConfig;
    private final SadadWsBillsRepository       sadadWsBillsRepo;
    private final SadadWsAuditRequestRepository auditRepo;

    @PostConstruct
    public void init() throws MalformedURLException {
        /*
         * In production (with real keystore files present), call:
         *   sslConfig.initSsl();
         *
         * Guarded here so the app starts cleanly in development / H2 mode
         * without a real keystore.
         */

        URL wsdlUrl = new URL(tahseelProps.getWsdlUrl());

        sslConfig.initSsl();  // 🔥 REQUIRED

        wsdlUrl = getClass().getClassLoader().getResource("BillsMngAGW.wsdl");

        if (wsdlUrl == null) {
            throw new RuntimeException("WSDL not found in resources");
        }
        log.info("[Tahseel] Gateway service initialized. WSDL target: {}", tahseelProps.getWsdlUrl());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Public API — ITahseelGatewayService
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public String addBill(SadadWsBills bill, List<RevenueEntry> revenueEntries) {
        log.info("[Tahseel] ADD bill={} amount={} beneficiary={}",
                bill.getBillNo(), bill.getBillAmount(), bill.getBenName());
        return callTahseel(bill, "I", null, revenueEntries);
    }

    @Override
    public String updateBill(SadadWsBills bill, List<RevenueEntry> revenueEntries) {
        log.info("[Tahseel] UPDATE bill={}", bill.getBillNo());
        return callTahseel(bill, "U", null, revenueEntries);
    }

    @Override
    public String cancelBill(String billNo, String agencyId, String actionReason) {
        log.info("[Tahseel] CANCEL bill={} reason={}", billNo, actionReason);

        SadadWsBills cancelBill = SadadWsBills.builder()
                .billNo(billNo)
                .agencyId(agencyId)
                .billAction("E")
                .actionReason(actionReason)
                .build();

        return callTahseel(cancelBill, "E", actionReason, Collections.emptyList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Core SOAP dispatch — mirrors original BillMngAGW.sendBillToTahseel()
    // ─────────────────────────────────────────────────────────────────────────

    private String callTahseel(SadadWsBills bill,
                               String action,
                               String actionReason,
                               List<RevenueEntry> revenueEntries) {

        String resultCode;
        String requestUuid = UUID.randomUUID().toString();

        try {
            // 🔐 Initialize SSL (mandatory)
            sslConfig.initSsl();

            // ================= HEADER =================
            MsgRqHdrType hdr = new MsgRqHdrType();
            hdr.setRqUID(requestUuid);
            hdr.setSCId(SCIdType.G_2_G);
            hdr.setFuncId(tahseelProps.getBillManageFuncId());

            PartnerInfoType partnerInfo = new PartnerInfoType();
            partnerInfo.setPartnerId(tahseelProps.getPartnerId());
            partnerInfo.setPartnerType(PartnerTypeType.GOVT);
            hdr.setPartnerInfo(partnerInfo);

            GregorianCalendar regCal = (GregorianCalendar) Calendar.getInstance();
            hdr.setClientDt(DatatypeFactory.newInstance().newXMLGregorianCalendar(regCal));

            // ================= BILL =================
            BillInfoType billInfo = new BillInfoType();
            billInfo.setAgencyId(tahseelProps.getAgencyId());
            billInfo.setBillAcct(bill.getBillNo());
            billInfo.setBillAction(BillActionType.valueOf(action));
            billInfo.setBillCategory(bill.getBillCategory());

            String labelAr = bill.getDisplayLabelAr();
            if (labelAr != null) {
                billInfo.setDisplayLabelAr(labelAr.length() > 31 ? labelAr.substring(0, 31) : labelAr);
            }

            billInfo.setBillAmt(bill.getBillAmount());
            billInfo.setDueDt(DATE_FMT.format(bill.getDueDate().toInstant().atZone(ZoneId.of("UTC"))));

            if (!"E".equals(action)) {
                billInfo.setExpDt(DATE_FMT.format(bill.getExpireDate().toInstant().atZone(ZoneId.of("UTC"))));
            } else {
                billInfo.setActionReason(actionReason);
            }

            String refInfo = bill.getBillRefInfo();
            if (refInfo != null) {
                billInfo.setBillRefInfo(refInfo.length() > 40 ? refInfo.substring(0, 40) : refInfo);
            }

            // ================= BENEFICIARY =================
            BenInfoType benInfo = new BenInfoType();
            POIType poi = new POIType();
            poi.setPOINum(bill.getPoiNum());
            poi.setPOIType(POITypeType.fromValue(bill.getPoiType()));
            benInfo.setBenPOI(poi);

            String benName = bill.getBenName();
            if (benName != null) {
                benInfo.setBenName(benName.length() > 278 ? benName.substring(0, 278) : benName);
            }

            billInfo.setBenInfo(benInfo);

            // ================= REVENUE =================
            Map<String, BigDecimal> grouped = groupRevenueEntries(revenueEntries);

            RevenueEntryListType revList = new RevenueEntryListType();
            for (Map.Entry<String, BigDecimal> e : grouped.entrySet()) {
                RevenueEntryInfoType rev = new RevenueEntryInfoType();
                rev.setGFSCode(e.getKey());
                rev.setAmt(e.getValue());
                rev.setBenAgencyId(tahseelProps.getAgencyId());
                revList.getRevenueEntryInfo().add(rev);
            }

            billInfo.setRevenueEntryList(revList);

            // ================= BODY =================
            BillListType billList = new BillListType();
            billList.getBillInfo().add(billInfo);

            BillsMngAGWRqBodyType body = new BillsMngAGWRqBodyType();
            body.setBatchId(UUID.randomUUID().toString());
            body.setBillList(billList);

            BillsMngAGWRqType request = new BillsMngAGWRqType();
            request.setMsgRqHdr(hdr);
            request.setBody(body);

            // ================= SOAP CLIENT =================
            URL wsdl = getClass().getClassLoader().getResource("BillsMngAGW.wsdl");

            if (wsdl == null) {
                throw new RuntimeException("WSDL not found in resources");
            }

            BillsMngAGW_Service svc = new BillsMngAGW_Service(wsdl);
            BillsMngAGW port = svc.getBillsMngAGWSOAP();

            BindingProvider bp = (BindingProvider) port;

            // 🔥 Set endpoint BEFORE call
            Map<String, Object> ctx = bp.getRequestContext();
            ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, tahseelProps.getWsdlUrl());
            ctx.put("com.sun.xml.ws.connect.timeout", 15000);
            ctx.put("com.sun.xml.ws.request.timeout", 30000);

            // 🔐 Attach WS-Security handler
            Binding binding = bp.getBinding();
            List<Handler> handlers = new ArrayList<>();
            handlers.add(tahseelWsSecurityHandler());
            binding.setHandlerChain(handlers);

            // ================= CALL =================
            BillsMngAGWRsType response = port.billsMngAGW(request);

            resultCode = response.getMsgRsHdr().getStatus().getStatusCode();
            requestUuid = response.getMsgRsHdr().getRqUID();

            log.info("[Tahseel] bill={} action={} result={}",
                    bill.getBillNo(), action, resultCode);

        } catch (Exception e) {
            log.error("[Tahseel] SOAP call failed for bill={}", bill.getBillNo(), e);
            resultCode = STATUS_NETWORK_ERROR;
        }

        // ================= AUDIT =================
        saveAudit(requestUuid,
                tahseelProps.getBillManageFuncId(),
                resultCode
                 );

        // ================= UPDATE DB =================
        if (STATUS_SUCCESS.equals(resultCode)) {
            String finalRequestUuid = requestUuid;
            sadadWsBillsRepo.findByBillNo(bill.getBillNo()).ifPresent(wb -> {
                wb.setReqId(finalRequestUuid);
                sadadWsBillsRepo.save(wb);
            });
        }

        saveAudit(requestUuid, tahseelProps.getBillManageFuncId(), resultCode);
        return resultCode;
    }

    private Handler tahseelWsSecurityHandler() {
        return new TahseelSecurityHandler();
    }

//    private String callTahseel(SadadWsBills bill,
//                                String action,
//                                String actionReason,
//                                List<RevenueEntry> revenueEntries) {
//        String resultCode;
//        String requestUuid = UUID.randomUUID().toString();
//
//        try {
//            /*
//             * ─────────────────────────────────────────────────────────────────
//             * SOAP REQUEST CONSTRUCTION
//             * ─────────────────────────────────────────────────────────────────
//             * The code below mirrors the original BillMngAGW logic exactly.
//             * Variable names are kept identical for easy cross-reference.
//             *
//             * When you have the WSDL-generated stubs from the real MOF WSDL,
//             * replace the comment blocks with the actual class instantiations.
//             * Every field set below corresponds 1-to-1 to the original.
//             * ─────────────────────────────────────────────────────────────────
//             *
//             * STEP 1 — Build MsgRqHdrType (message header)
//             */
//                MsgRqHdrType hdr = new MsgRqHdrType();
//                hdr.setRqUID(requestUuid);                       // UUID per call
//                hdr.setSCId(SCIdType.G_2_G);                    // G2G channel
//                hdr.setFuncId(tahseelProps.getBillManageFuncId());
//
//               PartnerInfoType partnerInfo = new PartnerInfoType();
//               partnerInfo.setPartnerId(tahseelProps.getPartnerId());
//               partnerInfo.setPartnerType(PartnerTypeType.GOVT);
//              hdr.setPartnerInfo(partnerInfo);
//
//               GregorianCalendar regCal = (GregorianCalendar) Calendar.getInstance();
//               hdr.setClientDt(DatatypeFactory.newInstance().newXMLGregorianCalendar(regCal));
//
//              //STEP 2 — Build BillInfoType (the bill record)
//
//               BillInfoType billInfo = new BillInfoType();
//                billInfo.setAgencyId(tahseelProps.getAgencyId());
//                billInfo.setBillAcct(bill.getBillNo());
//                billInfo.setBillAction(BillActionType.valueOf(action));   // A / U / E
//                billInfo.setBillCategory(bill.getBillCategory());
//
//                String labelAr = bill.getDisplayLabelAr();
//                billInfo.setDisplayLabelAr(labelAr != null && labelAr.length() > 31
//                        ? labelAr.substring(0, 31) : labelAr);
//
//                billInfo.setBillAmt(bill.getBillAmount());
//                billInfo.setDueDt(DATE_FMT.format(bill.getDueDate().toInstant().atZone(ZoneId.of("UTC"))));
//
//                if (!"E".equals(action)) {
//                    billInfo.setExpDt(DATE_FMT.format(bill.getExpireDate().toInstant().atZone(ZoneId.of("UTC"))));
//                } else {
//                    billInfo.setActionReason(actionReason);
//                }
//
//                String refInfo = bill.getBillRefInfo();
//                if (refInfo != null)
//                    billInfo.setBillRefInfo(refInfo.length() > 40 ? refInfo.substring(0, 40) : refInfo);
//
//              //STEP 3 — Set BenInfo (beneficiary / POI)
//
//                BenInfoType benInfo = new BenInfoType();
//                POIType poi = new POIType();
//                poi.setPOINum(bill.getPoiNum());
//                poi.setPOIType(POITypeType.fromValue(bill.getPoiType()));
//                benInfo.setBenPOI(poi);
//                String benName = bill.getBenName();
//                if (benName != null)
//                    benInfo.setBenName(benName.length() > 278 ? benName.substring(0, 278) : benName);
//                billInfo.setBenInfo(benInfo);
//
//             // STEP 4 — Group revenue entries by GFS code and build RevenueEntryListType
//
//                Map<String, BigDecimal> grouped = groupRevenueEntries(revenueEntries);
//                RevenueEntryListType revList = new RevenueEntryListType();
//                for (Map.Entry<String, BigDecimal> e : grouped.entrySet()) {
//                  RevenueEntryInfoType rev = new RevenueEntryInfoType();
//                    rev.setGFSCode(e.getKey());
//                    rev.setAmt(e.getValue());
//                    rev.setBenAgencyId(tahseelProps.getAgencyId());
//                    revList.getRevenueEntryInfo().add(rev);
//                }
//                billInfo.setRevenueEntryList(revList);
//
//             // STEP 5 — Assemble request and call the web service
//
//                BillsMngAGWRqBodyType body = new BillsMngAGWRqBodyType();
//                body.setBatchId(UUID.randomUUID().toString());
//                BillListType billList = new BillListType();
//                billList.getBillInfo().add(billInfo);
//                body.setBillList(billList);
//
//               BillsMngAGWRqType request = new BillsMngAGWRqType();
//                request.setMsgRqHdr(hdr);
//                request.setBody(body);
//
//               BillsMngAGW_Service svc = new BillsMngAGW_Service(new URL(tahseelProps.getWsdlUrl()),
//                        new QName("https://www.ecp.mof.gov.sa/agw", "BillsMngAGW_Service"));
//                BillsMngAGW port = svc.getBillsMngAGWSOAP();
//
//                // Attach WS-Security signing handler (mirrors original HeaderHandler)
//                Binding binding = ((BindingProvider) port).getBinding();
//                List<Handler> handlers = binding.getHandlerChain();
//                handlers.add(tahseelWsSecurityHandler());
//                binding.setHandlerChain(handlers);
//
//                BillsMngAGWRsType response = port.billsMngAGW(request);
//              resultCode = response.getMsgRsHdr().getStatus().getStatusCode();
//               requestUuid = response.getMsgRsHdr().getRqUID();    // echo back actual UUID
//
//            // ── Placeholder: simulate success in dev/test ─────────────────────
//            log.info("[Tahseel] Dispatching bill={} action={} (WSDL stub placeholder)",
//                    bill.getBillNo(), action);
//
//
//
//// set endpoint (important if different from WSDL)
//            Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
//            ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, tahseelProps.getWsdlUrl());
//
//// timeouts
//            ctx.put("com.sun.xml.ws.connect.timeout", 15000);
//            ctx.put("com.sun.xml.ws.request.timeout", 30000);
//
//// call
//
//            resultCode = response.getMsgRsHdr().getStatus().getStatusCode();
//            requestUuid = response.getMsgRsHdr().getRqUID();
//
//            resultCode = STATUS_SUCCESS;  // replace with actual SOAP call result
//
//        } catch (Exception e) {
//            log.error("[Tahseel] SOAP call failed for bill={}", bill.getBillNo(), e);
//            resultCode = STATUS_NETWORK_ERROR;
//        }
//
//        // ── Audit every call regardless of outcome ────────────────────────────
//        saveAudit(requestUuid, tahseelProps.getBillManageFuncId(), resultCode,
//                "bill=" + bill.getBillNo() + " action=" + action);
//
//        // ── Update REQID on the SadadWsBills record ───────────────────────────
//        if (STATUS_SUCCESS.equals(resultCode)) {
//            String finalRequestUuid = requestUuid;
//            sadadWsBillsRepo.findByBillNo(bill.getBillNo()).ifPresent(wb -> {
//                wb.setReqId(finalRequestUuid);
//                sadadWsBillsRepo.save(wb);
//            });
//        }
//
//        log.info("[Tahseel] bill={} action={} result={}", bill.getBillNo(), action, resultCode);
//        return resultCode;
//    }



    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Group revenue entries by GFS code and sum amounts — mirrors original logic:
     * revenueEntryList.stream().collect(groupingBy(accountNo, reducing(ZERO, amount, add)))
     */
    private Map<String, BigDecimal> groupRevenueEntries(
            List<RevenueEntry> entries) {

        return entries.stream()
                .collect(Collectors.groupingBy(
                        RevenueEntry::getGfsCode,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                RevenueEntry::getAmt,
                                BigDecimal::add)));
    }
//    Map<String, BigDecimal> groupRevenueEntries(List<RevenueEntry> entries) {
//        return entries.stream()
//                .collect(Collectors.groupingBy(
//                        RevenueEntry::getAccountNo,
//                        Collectors.reducing(BigDecimal.ZERO,
//                                RevenueEntry::getAmount,
//                                BigDecimal::add)));
//    }

    private void saveAudit(String requestId, String functionId,
                            String statusCode) {
        try {
            SadadWsAuditRequest audit = SadadWsAuditRequest.builder()
                    .requestId(requestId)
                    .functionId(functionId)
                    .message(statusCode)
                    .createdDate(new Date())
                    .build();
            auditRepo.save(audit);
        } catch (Exception e) {
            log.error("[Tahseel] Failed to save audit record", e);
        }
    }
}
