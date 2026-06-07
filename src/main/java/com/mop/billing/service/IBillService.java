package com.mop.billing.service;

import com.mop.billing.dto.request.BillCancelRequest;
import com.mop.billing.dto.request.BillCreateRequest;
import com.mop.billing.dto.request.GenerateBillRequest;
import com.mop.billing.dto.response.BillResponse;
import com.mop.billing.dto.response.BillStatusResponse;

import java.util.List;

/**
 * Service interface — Bill generation and lifecycle management.
 * Implementations live in service/impl.
 */
public interface IBillService {

    /**
     * Create a new bill, persist it locally, and dispatch it to Tahseel.
     * @return the generated bill number
     */
    //BillResponse createBill(BillCreateRequest request);

    BillResponse generateBill(
            GenerateBillRequest request) throws Exception;
    /**
     * Cancel an existing bill in both local DB and Tahseel.
     */
    void cancelBill(BillCancelRequest request);

    /**
     * Get bill status by bill number.
     */
    BillStatusResponse getBillStatus(Long billNumber);

    /**
     * Get all unpaid bills for an investor.
     */
    List<BillResponse> getUnpaidBillsByInvestor(Integer invId);

    /**
     * Retry dispatching bills that failed or were never sent to Tahseel.
     * Called by a scheduled job.
     */
    void retryPendingTahseelDispatch();
}
