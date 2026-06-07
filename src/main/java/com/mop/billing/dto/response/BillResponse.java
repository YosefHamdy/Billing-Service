package com.mop.billing.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

/** Returned by POST /api/bills and GET /api/bills/{billNumber} */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BillResponse {

    private Long       billNumber;
    private Integer    licNumber;
    private String     licType;
    private String     billOwnerName;
    private BigDecimal totalAmount;
    private Integer    billStatus;
    private String     billStatusLabel;   // UNPAID / PAID / CANCELLED
    private Integer    billType;
    private Date       billDateG;
    private Date       dueDate;
    private String     tahseelStatusCode; // I000000 = sent OK, null = not yet sent
    private String     notes;
}
