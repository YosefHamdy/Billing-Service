package com.mop.billing.producer.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

// ─────────────────────────────────────────────────────────────────────────────
// mop.tahseel.sent
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Kafka event payload — published to topic: mop.tahseel.sent
 *
 * Fired when a bill is successfully dispatched to Tahseel (initial or retry).
 *
 * ─── CONSUMER GUIDANCE ────────────────────────────────────────────────────
 * Monitoring Service → confirm SLA: bill was sent within X minutes of creation
 * Retry Tracker      → remove bill from "pending dispatch" watchlist
 * ──────────────────────────────────────────────────────────────────────────
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class TahseelSentEvent {
    private String  eventType   = "TAHSEEL_SENT";
    private String  billNo;
    private String  tahseelReqId;
    private String  statusCode;
    private boolean isRetry;
    private Long    timestamp;
}

// ─────────────────────────────────────────────────────────────────────────────
// mop.tahseel.failed
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Kafka event payload — published to topic: mop.tahseel.failed
 *
 * Fired when Tahseel returns a non-success code or a network error occurs.
 *
 * ─── CONSUMER GUIDANCE ────────────────────────────────────────────────────
 * Alert Service    → page on-call if errorCode = NETWORK_ERROR
 * Retry Service    → schedule exponential-backoff retry
 * Dashboard        → show "Tahseel sync issue" banner to admin
 * ──────────────────────────────────────────────────────────────────────────
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class TahseelFailedEvent {
    private String eventType  = "TAHSEEL_DISPATCH_FAILED";
    private String billNo;
    private String errorCode;
    private Long   timestamp;
}

// ─────────────────────────────────────────────────────────────────────────────
// mop.payment.received
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Kafka event payload — published to topic: mop.payment.received
 *
 * Fired after PmtNotificationAGW SOAP callback is parsed and persisted.
 * Published BEFORE the bill status is updated — consumers should treat
 * this as "raw inbound payment" and wait for mop.bill.paid for confirmation.
 *
 * ─── CONSUMER GUIDANCE ────────────────────────────────────────────────────
 * SAP Service      → write to SAP_SYNC_HUB (INVOICE_PAYMENT process=4)
 * NIC Service      → cancelNicFinePayment if fine bill
 * Audit Service    → store raw payment record for audit trail
 * ──────────────────────────────────────────────────────────────────────────
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class PaymentReceivedEvent {
    private String  eventType        = "PAYMENT_RECEIVED";
    private String  billNo;
    private Double  pmtAmount;
    private String  pmtStatusCode;
    private String  eCollectionPmtId;
    private String  ecollectionPmtMethod;
    private String  pmtRefInfo;
    private String  reqUId;
    private Long    timestamp;
}

// ─────────────────────────────────────────────────────────────────────────────
// mop.reconciliation.received
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Kafka event payload — published to topic: mop.reconciliation.received
 *
 * Fired after AgencyReconRptAGW SOAP callback is parsed.
 *
 * ─── CONSUMER GUIDANCE ────────────────────────────────────────────────────
 * Finance Service  → compare Tahseel totals vs local DB totals
 *                    and flag any discrepancies
 * Reporting        → daily settlement report generation
 * Audit Service    → archive reconciliation report
 * ──────────────────────────────────────────────────────────────────────────
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class ReconciliationReceivedEvent {
    private String  eventType          = "RECONCILIATION_RECEIVED";
    private String  reqUId;
    private String  agencyId;
    private Long    reconDate;
    private Integer totalTransactions;
    private Double  totalAmount;
    private Long    timestamp;
}
