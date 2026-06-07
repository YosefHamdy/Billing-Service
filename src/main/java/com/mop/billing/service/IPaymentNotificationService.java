package com.mop.billing.service;

import com.mop.billing.dto.request.PaymentNotificationRequest;
import com.mop.billing.dto.request.ReconciliationRequest;

/**
 * Service interface — handles inbound payment events pushed by Tahseel/Sadad.
 */
public interface IPaymentNotificationService {

    /**
     * Process an inbound payment notification from Tahseel (PmtNotificationAGW).
     * Validates the request, marks the bill as paid, fires a Kafka event.
     *
     * @return Tahseel response code ("I000000" = success, "E200xxx" = error)
     */
    String processPaymentNotification(PaymentNotificationRequest request);

    /**
     * Process an inbound agency reconciliation report from Tahseel (AgencyReconRptAGW).
     *
     * @return Tahseel response code
     */
    String processReconciliation(ReconciliationRequest request);
}
