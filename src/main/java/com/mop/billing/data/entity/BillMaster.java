package com.mop.billing.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Maps to SADAD_BILLS_MASTER — the central bill record.
 * billStatus: 0 = unpaid, 1 = paid, 2 = cancelled.
 * billType:   1 = claim,  2 = fine.
 */
@Entity
@Table(name = "SADAD_BILLS_MASTER")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BillMaster {

    @Id
    @Column(name = "BILL_NO")
    private Long billNumber;

    @Column(name = "LIC_NO")       private Integer licNumber;
    @Column(name = "LIC_TYPE")     private String  licType;

    /** 0 = unpaid, 1 = paid, 2 = cancelled */
    @Column(name = "BILL_STATUS")  private Integer billStatus;

    @Column(name = "BILL_PAY_TYPE")  private String  billPayType;
    @Column(name = "COLLECT_BY")     private Integer collectBy;
    @Column(name = "COLLECTION_SR")  private String  collectionSr;

    @Column(name = "PAY_DATE_H")     private String  payDateH;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAY_DATE_G")     private Date    payDateG;

    @Column(name = "PAY_AMOUNT",  precision = 18, scale = 2)
    private BigDecimal payAmount;

    @Column(name = "BILL_O_NAME")  private String billOwnerName;
    @Column(name = "BILL_DATE")    private String billDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BILL_DATE_G")  private Date   billDateG;

    @Column(name = "BANK_CODE")       private Integer bankCode;
    @Column(name = "CHEQUE_NO")       private Long    chequeNo;
    @Column(name = "PAY_DESC")        private String  deiscription;
    @Column(name = "PAY_DATE_FROM")   private String  payDateFrom;
    @Column(name = "PAY_DATE_TO")     private String  payDateTo;
    @Column(name = "PAY_INSTALL_NO")  private Long    payInstallNo;
    @Column(name = "CHEQUE_DATE")     private String  chequeDate;
    @Column(name = "PAID_BY")         private String  paidBy;
    @Column(name = "INV_ID")          private Integer invId;

    @Column(name = "REF_ACTION_TYPE") private Integer referenceType;
    @Column(name = "REF_NO")          private Integer referenceNumber;
    @Column(name = "created_by")      private Integer createdBy;

    @Column(name = "NAT_ID")          private String nationalId;
    @Column(name = "cr")              private String cr;
    @Column(name = "CR_NAT_ID")       private String crNationalId;
    @Column(name = "notes")           private String notes;

    /** 1 = claim, 2 = fine */
    @Column(name = "BILL_TYPE")  private Integer billType;

    /** 1 = active, 0 = not active */
    @Column(name = "active")  private Integer active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CANCELED_DATE")  private Date    canceledDate;
    @Column(name = "CANCEL_REASON_ID") private Integer cancelReasonId;
    @Column(name = "CANCEL_NOTE")      private String  cancelNote;
    @Column(name = "canceled_by")      private Integer canceledBy;

    // ── Convenience ──────────────────────────────────────────────────────────
    public boolean isPaid()      { return Integer.valueOf(1).equals(billStatus); }
    public boolean isCancelled() { return Integer.valueOf(2).equals(billStatus); }
    public boolean isUnpaid()    { return Integer.valueOf(0).equals(billStatus); }
}
