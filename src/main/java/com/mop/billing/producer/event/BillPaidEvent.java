package com.mop.billing.producer.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.Date;

/**
 * Kafka event payload — published to topic: mop.bill.paid
 *
 * Fired after PmtNotificationAGW is processed and BillMaster.billStatus = 1.
 *
 * ─── CONSUMER GUIDANCE ────────────────────────────────────────────────────
 * License Service      → activate / renew the license that was pending payment
 * SAP Service          → send INVOICE_PAYMENT (process id=4) to SAP via SAP_SYNC_HUB
 * NIC Service          → call cancelNicFinePayment(billNo) if fine bill
 * Notification Service → send SMS/email to investor: "Payment confirmed"
 * Reporting Service    → real-time revenue dashboard update
 * ──────────────────────────────────────────────────────────────────────────
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaidEvent {

    private String  eventType        = "BILL_PAID";
    private String  billNo;
    private Integer invId;
    private Double  pmtAmount;
    private Date    pmtDate;
    /** Payment method: SADAD / ATM / MADA / etc. */
    private String  ecollectionPmtMethod;
    private String  eCollectionPmtId;
    private String  pmtRefInfo;
    /** I000000 = success from Tahseel */
    private String  pmtStatusCode;
    /** Sadad request UUID — use for reconciliation matching */
    private String  reqUId;
    private Long    timestamp;
}
