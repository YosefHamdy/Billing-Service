package com.mop.billing.dto.request;

import lombok.*;
import java.util.Date;

/**
 * Internal DTO populated after parsing the inbound Tahseel PmtNotificationAGW SOAP body.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentNotificationRequest {

    private String reqUId;
    private String functionId;
    private String partnerId;

    private String billNo;
    private String eCollectionPmtId;
    private String billCategory;
    private Double pmtAmount;
    private Date   pmtDate;
    private String pmtStatusCode;
    private String pmtRefInfo;
    private String ecollectionPmtMethod;
    private String payorPoiNum;
    private String payorPoiType;
}
