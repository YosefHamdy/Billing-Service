package com.mop.billing.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Maps to SADAD_WS_BILLS_DET — the per-GFS-code revenue breakdown sent to Tahseel.
 * Multiple rows can exist per bill; rows with the same GFS code are summed before dispatch.
 */
@Entity
@Table(name = "SADAD_WS_BILLS_DET")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@SequenceGenerator(name = "sadad_ws_bills_det_seq", sequenceName = "SADAD_WS_BILLS_DET_SEQ", allocationSize = 1)
public class SadadWsDet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sadad_ws_bills_det_seq")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "bill_no")     private Long       billNo;

    /** GFS (Government Financial System) account code */
    @Column(name = "account_no")  private String     accountNo;

    @Column(name = "AMT", precision = 18, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FROMDATE")   private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TODATE")     private Date toDate;

    @Column(name = "PAYIDDET")   private Integer payIdDetail;
}
