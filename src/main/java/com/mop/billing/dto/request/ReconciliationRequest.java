package com.mop.billing.dto.request;

import lombok.*;
import java.util.Date;

/**
 * Internal DTO populated after parsing the inbound AgencyReconRptAGW SOAP body.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReconciliationRequest {

    private String  reqUId;
    private String  functionId;
    private String  partnerId;
    private Date    reconciliationDate;
    private String  agencyId;
    private Integer totalTransactions;
    private Double  totalAmount;
}
