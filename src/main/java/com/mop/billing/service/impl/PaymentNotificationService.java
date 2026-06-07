package com.mop.billing.service.impl;

import com.mop.billing.config.TahseelProperties;
import com.mop.billing.data.entity.*;
import com.mop.billing.data.repository.*;
import com.mop.billing.dto.request.PaymentNotificationRequest;
import com.mop.billing.dto.request.ReconciliationRequest;
import com.mop.billing.producer.BillingEventProducer;
import com.mop.billing.service.IPaymentNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Business Layer — Inbound payment processing.
 *
 * Handles callbacks from Tahseel/Sadad:
 *   - PmtNotificationAGW  → processPaymentNotification()
 *   - AgencyReconRptAGW   → processReconciliation()
 *
 * Faithfully ports the original PmtNotificationAGW.processPaymentNotification()
 * and AgencyReconRptAGW business logic.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentNotificationService implements IPaymentNotificationService {

    // Tahseel response codes (mirrors original Constants)
    private static final String CODE_SUCCESS          = "I000000";
    private static final String CODE_INVALID_PARTNER  = "E200996";
    private static final String CODE_GENERAL_ERROR    = "E200999";

    private final BillMasterRepository       billMasterRepo;
    private final PmtInfoRepository          pmtInfoRepo;
    private final SadadWsAuditRequestRepository auditRepo;
    private final BillingEventProducer       eventProducer;
    private final TahseelProperties          tahseelProps;

    // ─────────────────────────────────────────────────────────────────────────
    // PAYMENT NOTIFICATION (PmtNotificationAGW callback)
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public String processPaymentNotification(PaymentNotificationRequest req) {
        log.info("[PaymentNotification] Received payment for bill={} amount={} status={}",
                req.getBillNo(), req.getPmtAmount(), req.getPmtStatusCode());

        // 1. Validate message header (partner + function IDs)
        if (!validateMessageHeader(req.getPartnerId(), req.getFunctionId(),
                tahseelProps.getPmtNotificationFuncId())) {
            log.warn("[PaymentNotification] Header validation failed for reqId={}", req.getReqUId());
            saveAudit(req.getReqUId(), req.getFunctionId(), CODE_INVALID_PARTNER);
            return CODE_INVALID_PARTNER;
        }

        try {
            // 2. Persist PmtInfo record — mirrors original savePaymentNotification()
            PmtInfo pmtInfo = PmtInfo.builder()
                    .billNo(req.getBillNo())
                    .eCollectionPmtId(req.getECollectionPmtId())
                    .billCategory(req.getBillCategory())
                    .pmtAmount(req.getPmtAmount())
                    .pmtDate(req.getPmtDate())
                    .pmtStatusCode(req.getPmtStatusCode())
                    .pmtRefInfo(req.getPmtRefInfo())
                    .ecollectionPmtMethod(req.getEcollectionPmtMethod())
                    .createdDate(new Date())
                    .reqId(req.getReqUId())
                    .build();
            pmtInfoRepo.save(pmtInfo);

            // 3. Mark bill as paid in BillMaster — mirrors original flow
            if (req.getBillNo() != null) {
                try {
                    Long billNo = Long.parseLong(req.getBillNo());
                    billMasterRepo.findByBillNumber(billNo).ifPresent(bill -> {
                        if (!bill.isPaid()) {
                            bill.setBillStatus(1);          // 1 = PAID
                            bill.setPayDateG(req.getPmtDate());
                            bill.setPayAmount(
                                    req.getPmtAmount() != null
                                            ? java.math.BigDecimal.valueOf(req.getPmtAmount())
                                            : bill.getPayAmount());
                            billMasterRepo.save(bill);
                            log.info("[PaymentNotification] Bill={} marked as PAID", billNo);
                        }
                    });
                } catch (NumberFormatException e) {
                    log.warn("[PaymentNotification] Non-numeric bill number: {}", req.getBillNo());
                }
            }

            // 4. Publish to Kafka
            //    mop.payment.received → SAP Service, NIC Service, Audit
            eventProducer.paymentReceived(req.getBillNo(), req.getPmtAmount(),
                    req.getPmtStatusCode(), req.getReqUId());
            //    mop.bill.paid → License Service, Notification Service, Reporting
            eventProducer.billPaid(
                    req.getBillNo(),
                    req.getPmtAmount(),
                    req.getPmtDate(),
                    req.getEcollectionPmtMethod(),
                    req.getECollectionPmtId(),
                    req.getPmtRefInfo(),
                    req.getPmtStatusCode(),
                    req.getReqUId());

            // 5. Audit record
            saveAudit(req.getReqUId(), req.getFunctionId(), CODE_SUCCESS);

            return CODE_SUCCESS;

        } catch (Exception e) {
            log.error("[PaymentNotification] Processing failed for bill={}", req.getBillNo(), e);
            saveAudit(req.getReqUId(), req.getFunctionId(), CODE_GENERAL_ERROR);
            return CODE_GENERAL_ERROR;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // RECONCILIATION (AgencyReconRptAGW callback)
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public String processReconciliation(ReconciliationRequest req) {
        log.info("[Reconciliation] Received recon report date={} agencyId={} txCount={} total={}",
                req.getReconciliationDate(), req.getAgencyId(),
                req.getTotalTransactions(), req.getTotalAmount());

        if (!validateMessageHeader(req.getPartnerId(), req.getFunctionId(), null)) {
            saveAudit(req.getReqUId(), req.getFunctionId(), CODE_INVALID_PARTNER);
            return CODE_INVALID_PARTNER;
        }

        try {
            // Publish Kafka event so downstream services can compare with local totals
            eventProducer.reconciliationReceived(
                    req.getReqUId(),
                    req.getReconciliationDate(),
                    req.getTotalTransactions(),
                    req.getTotalAmount());

            saveAudit(req.getReqUId(), req.getFunctionId(), CODE_SUCCESS);
            return CODE_SUCCESS;

        } catch (Exception e) {
            log.error("[Reconciliation] Processing failed", e);
            saveAudit(req.getReqUId(), req.getFunctionId(), CODE_GENERAL_ERROR);
            return CODE_GENERAL_ERROR;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Validates the Sadad/Tahseel message header.
     * Mirrors the original validateMessageHeader() — checks partnerId matches
     * and functionId matches expected value.
     */
    private boolean validateMessageHeader(String partnerId, String functionId,
                                           String expectedFunctionId) {
        if (partnerId != null
                && !tahseelProps.getPartnerId().equals(partnerId)) {
            log.warn("[Validation] Partner ID mismatch: got={} expected={}",
                    partnerId, tahseelProps.getPartnerId());
            return false;
        }
        if (expectedFunctionId != null
                && functionId != null
                && !expectedFunctionId.equals(functionId)) {
            log.warn("[Validation] Function ID mismatch: got={} expected={}",
                    functionId, expectedFunctionId);
            return false;
        }
        return true;
    }

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
            log.error("[Audit] Failed to save audit record", e);
        }
    }
}
