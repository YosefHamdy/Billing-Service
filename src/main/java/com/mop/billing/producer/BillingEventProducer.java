package com.mop.billing.producer;

import com.mop.billing.config.KafkaTopicProperties;
import com.mop.billing.producer.event.BillCancelledEvent;
import com.mop.billing.producer.event.BillCreatedEvent;
import com.mop.billing.producer.event.BillPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Producer Layer — publishes all billing domain events to Kafka.
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  TOPIC                        │ KEY          │ PAYLOAD CLASS             │
 * ├───────────────────────────────┼──────────────┼───────────────────────────┤
 * │  mop.bill.created             │ billNumber   │ BillCreatedEvent          │
 * │  mop.bill.cancelled           │ billNumber   │ BillCancelledEvent        │
 * │  mop.bill.paid                │ billNo       │ BillPaidEvent             │
 * │  mop.tahseel.sent             │ billNo       │ Map<String,Object>        │
 * │  mop.tahseel.failed           │ billNo       │ Map<String,Object>        │
 * │  mop.payment.received         │ billNo       │ Map<String,Object>        │
 * │  mop.reconciliation.received  │ reqUId       │ Map<String,Object>        │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BillingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicProperties          topics;

    // mop.bill.created
    public void billCreated(Long billNumber, Integer invId, BigDecimal amount, String tahseelCode) {
        BillCreatedEvent event = BillCreatedEvent.builder()
                .billNumber(billNumber).invId(invId).totalAmount(amount)
                .tahseelStatusCode(tahseelCode).timestamp(System.currentTimeMillis()).build();
        send(topics.getBillCreated(), String.valueOf(billNumber), event);
    }

    public void billCreated(BillCreatedEvent event) {
        event.setTimestamp(System.currentTimeMillis());
        send(topics.getBillCreated(), String.valueOf(event.getBillNumber()), event);
    }

    // mop.bill.cancelled
    public void billCancelled(Long billNumber, String reason, String tahseelCode) {
        BillCancelledEvent event = BillCancelledEvent.builder()
                .billNumber(billNumber).cancelReason(reason)
                .tahseelStatusCode(tahseelCode).timestamp(System.currentTimeMillis()).build();
        send(topics.getBillCancelled(), String.valueOf(billNumber), event);
    }

    public void billCancelled(BillCancelledEvent event) {
        event.setTimestamp(System.currentTimeMillis());
        send(topics.getBillCancelled(), String.valueOf(event.getBillNumber()), event);
    }

    // mop.bill.paid
    public void billPaid(String billNo, Double amount, Date pmtDate,
                          String pmtMethod, String eCollectionPmtId,
                          String pmtRefInfo, String pmtStatusCode, String reqUId) {
        BillPaidEvent event = BillPaidEvent.builder()
                .billNo(billNo).pmtAmount(amount).pmtDate(pmtDate)
                .ecollectionPmtMethod(pmtMethod).eCollectionPmtId(eCollectionPmtId)
                .pmtRefInfo(pmtRefInfo).pmtStatusCode(pmtStatusCode)
                .reqUId(reqUId).timestamp(System.currentTimeMillis()).build();
        send(topics.getBillPaid(), billNo, event);
    }

    // mop.tahseel.sent
    public void tahseelRetrySuccess(String billNo, String statusCode) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType","TAHSEEL_SENT"); event.put("billNo",billNo);
        event.put("statusCode",statusCode); event.put("isRetry",true);
        event.put("timestamp",System.currentTimeMillis());
        send(topics.getTahseelSent(), billNo, event);
    }

    // mop.tahseel.failed
    public void tahseelFailed(String billNo, String errorCode) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType","TAHSEEL_DISPATCH_FAILED"); event.put("billNo",billNo);
        event.put("errorCode",errorCode); event.put("timestamp",System.currentTimeMillis());
        send(topics.getTahseelFailed(), billNo, event);
        log.warn("[Producer] Tahseel failure: bill={} error={}", billNo, errorCode);
    }

    // mop.payment.received
    public void paymentReceived(String billNo, Double amount, String pmtStatusCode, String reqUId) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType","PAYMENT_RECEIVED"); event.put("billNo",billNo);
        event.put("amount",amount); event.put("pmtStatusCode",pmtStatusCode);
        event.put("reqUId",reqUId); event.put("timestamp",System.currentTimeMillis());
        send(topics.getPmtReceived(), billNo, event);
    }

    // mop.reconciliation.received
    public void reconciliationReceived(String reqUId, Date reconDate, Integer totalTx, Double totalAmount) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType","RECONCILIATION_RECEIVED"); event.put("reqUId",reqUId);
        event.put("reconDate",reconDate != null ? reconDate.getTime() : null);
        event.put("totalTx",totalTx); event.put("totalAmount",totalAmount);
        event.put("timestamp",System.currentTimeMillis());
        send(topics.getReconReceived(), reqUId, event);
    }

    private void send(String topic, String key, Object payload) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, payload);
        future.whenComplete((result, ex) -> {
            if (ex != null) log.error("[Producer] FAILED topic={} key={}: {}", topic, key, ex.getMessage());
            else log.debug("[Producer] OK topic={} key={} offset={}", topic, key, result.getRecordMetadata().offset());
        });
    }
}
