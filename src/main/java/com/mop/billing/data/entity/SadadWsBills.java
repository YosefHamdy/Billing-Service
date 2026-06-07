package com.mop.billing.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Maps to SADAD_WS_BILLS — the record of every bill submitted to Tahseel (MOF eCollect gateway).
 * Created before the SOAP call; REQID is set after a successful Tahseel response.
 */
@Entity
@Table(name = "SADAD_WS_BILLS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@SequenceGenerator(name = "sadad_ws_bills_seq", sequenceName = "SADAD_WS_BILLS_SEQ", allocationSize = 1)
public class SadadWsBills {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sadad_ws_bills_seq")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "BILL_NO")         private String     billNo;
    @Column(name = "AGENCYID")        private String     agencyId;

    /** A = Add, U = Update, E = Expire/Cancel */
    @Column(name = "BILLACTION")      private String     billAction;
    @Column(name = "ACTIONREASON")    private String     actionReason;
    @Column(name = "BILLCATEGORY")    private String     billCategory;
    @Column(name = "BILLCYCLE")       private String     billCycle;
    @Column(name = "DISPLAYLABELAR")  private String     displayLabelAr;
    @Column(name = "DISPLAYLABELEN")  private String     displyLabelEn;

    @Column(name = "BILLAMT", precision = 18, scale = 2)
    private BigDecimal billAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DUEDT")           private Date dueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPDT")           private Date expireDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")    private Date createdDate;

    @Column(name = "CREATED_USER")    private Integer createdUser;

    @Column(name = "BILLREFINFO")     private String  billRefInfo;
    @Column(name = "BILLDESC")        private String  billDesc;

    @Column(name = "POINUM")          private String  poiNum;
    @Column(name = "POITYPE")         private String  poiType;
    @Column(name = "BENNAME")         private String  benName;

    @Column(name = "CR_NAT_ID")       private String  commercialRegAndNationalId;
    @Column(name = "CR_NAT_ID_TYPE")  private Integer commercialRegAndNationalType;

    @Column(name = "REJECTION_NOTE")  private String  rejectionNote;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REJECTION_DATE")  private Date    rejectionDate;
    @Column(name = "IS_REJECTED")     private Integer rejected;
    @Column(name = "IS_TRANSFERED")   private Integer transfered;

    /** Request-UUID returned by Tahseel on successful submission */
    @Column(name = "REQID")           private String reqId;
}
