package com.mop.billing.service;

import com.mop.billing.data.entity.RevenueEntry;
import com.mop.billing.data.entity.SadadWsBills;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service interface — wraps the outbound SOAP calls to the Tahseel/MOF gateway.
 * The implementation (TahseelGatewayService) handles XML signing, SSL, and audit logging.
 */
public interface ITahseelGatewayService {

    /**
     * Send a bill ADD request to Tahseel.
     *
     * @return Tahseel status code ("I000000" = accepted)
     */
    String addBill(SadadWsBills bill, List<RevenueEntry> revenueEntries);

    /**
     * Send a bill UPDATE request to Tahseel (amount or date change).
     *
     * @return Tahseel status code
     */
    String updateBill(SadadWsBills bill, List<RevenueEntry> revenueEntries);

    /**
     * Send a bill EXPIRE/CANCEL request to Tahseel.
     *
     * @param billNo       bill account number in Tahseel
     * @param actionReason reason code for cancellation
     * @return Tahseel status code
     */
    String cancelBill(String billNo, String agencyId, String actionReason);

    /**
     * Check whether a Tahseel response code represents success.
     */
    default boolean isSuccess(String statusCode) {
        return "I000000".equals(statusCode);
    }
}
