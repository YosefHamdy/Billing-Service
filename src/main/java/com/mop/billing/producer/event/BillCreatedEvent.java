package com.mop.billing.producer.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;

/**
 * Kafka event payload — published to topic: mop.bill.created
 *
 * Fired immediately after a bill is persisted locally AND a Tahseel dispatch
 * attempt has been made (whether successful or not).
 *
 * ─── CONSUMER GUIDANCE ────────────────────────────────────────────────────
 * License Service       → subscribe to know a fee bill was raised for a license
 * Notification Service  → send SMS/email to investor: "Your bill is ready"
 * Reporting Service     → update daily billing dashboard
 * ──────────────────────────────────────────────────────────────────────────
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillCreatedEvent {

    private String     eventType    = "BILL_CREATED";
    private Long       billNumber;
    private Integer    invId;
    private Integer    licNumber;
    private String     licType;
    private String     billOwnerName;
    private BigDecimal totalAmount;
    /** 1 = claim, 2 = fine */
    private Integer    billType;
    private Integer    referenceType;
    private Integer    referenceNumber;
    private String     billCategory;
    private String     nationalId;
    private String     poiType;
    /** Tahseel response code: I000000 = dispatched OK, NETWORK_ERROR = retry pending */
    private String     tahseelStatusCode;
    private Long       timestamp;
}
