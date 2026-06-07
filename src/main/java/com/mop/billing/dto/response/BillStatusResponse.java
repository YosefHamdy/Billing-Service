package com.mop.billing.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

/** Lightweight status check response */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BillStatusResponse {
    private Long    billNumber;
    private Integer billStatus;       // 0=unpaid, 1=paid, 2=cancelled
    private String  billStatusLabel;
    private BigDecimal paidAmount;
    private Date    payDateG;
    private String  tahseelReqId;     // Tahseel UUID from last WS call
}
