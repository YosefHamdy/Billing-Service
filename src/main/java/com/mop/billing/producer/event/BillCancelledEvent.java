package com.mop.billing.producer.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Kafka event payload — published to topic: mop.bill.cancelled
 *
 * ─── CONSUMER GUIDANCE ────────────────────────────────────────────────────
 * License Service  → un-block the license if it was held pending payment
 * NIC Service      → call cancelNicFinePayment if this was a fine bill
 * SAP Service      → send INVOICE_CANCEL (process id=5) to SAP
 * Reporting        → update cancelled bills report
 * ──────────────────────────────────────────────────────────────────────────
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillCancelledEvent {

    private String  eventType    = "BILL_CANCELLED";
    private Long    billNumber;
    private Integer invId;
    private String  cancelReason;
    private String  cancelNote;
    private Integer cancelledBy;
    /** Tahseel response code for the EXPIRE (E) action */
    private String  tahseelStatusCode;
    private Long    timestamp;
}
