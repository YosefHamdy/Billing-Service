package com.mop.billing.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Maps to SADAD_WS_PMT_NOTIFICATION.
 * Persisted when Tahseel/Sadad calls back with a payment notification (PmtNotificationAGW).
 */
@Entity
@Table(name = "SADAD_WS_PMT_NOTIFICATION")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PmtInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "BILL_NO")                      private String billNo;
    @Column(name = "ECOLLECTIONPMTID")             private String eCollectionPmtId;
    @Column(name = "BILLCATEGORY")                 private String billCategory;
    @Column(name = "BILLCYCLE")                    private String billCycle;
    @Column(name = "PMTAMT")                       private Double pmtAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMTDT")                        private Date pmtDate;

    @Column(name = "ECOLLECTIONPMTMETHOD")         private String ecollectionPmtMethod;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECOLLECTIONPMTMETHODRECONDT")  private Date eCollectionPmtMethodReconDt;

    @Column(name = "PMTREFINFO")                   private String pmtRefInfo;

    /** I000000 = success, E2xxxxx = error */
    @Column(name = "PMTSTATUSCODE")                private String pmtStatusCode;

    @Column(name = "RECONCILIATIONID")             private Integer reconciliationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")                 private Date createdDate;

    /** Sadad request UUID from MsgRqHeader */
    @Column(name = "REQID")                        private String reqId;
}
