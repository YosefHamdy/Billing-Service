package com.mop.billing.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Maps to SADAD_WS_AUDIT_REQUEST.
 * Every outbound (bill send/cancel) and inbound (payment notification) SOAP call
 * is recorded here for traceability and reconciliation.
 */
@Entity
@Table(name = "SADAD_WS_AUDIT_REQUEST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SadadWsAuditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    /** UUID echoed back by Tahseel in MsgRsHdr.RqUID */
    @Column(name = "REQID")         private String requestId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")  private Date createdDate;

    /** Tahseel status code: I000000 = OK, E2xxxxx = error */
    @Column(name = "MESSAGE")       private String message;

    /** FuncId from the SOAP header (e.g. BillsMng, PmtNotification) */
    @Column(name = "FUCTION_ID")    private String functionId;

//    /** Extra context: bill number, action type, etc. */
//    @Column(name = "NOTES")         private String notes;
}
