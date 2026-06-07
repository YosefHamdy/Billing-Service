package com.mop.billing.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mop.billing.dto.request.BillCreateRequest;
import com.mop.billing.dto.request.BillCancelRequest;
import com.mop.billing.dto.request.RevenueEntryRequest;
import com.mop.billing.service.IBillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Consumer Layer — This service consumes inbound Kafka events from OTHER projects.
 *
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │  TOPIC                       │ PUBLISHED BY        │ ACTION HERE            │
 * ├──────────────────────────────┼─────────────────────┼────────────────────────┤
 * │  mop.license.bill.request    │ License Service     │ Generate bill          │
 * │  mop.fine.bill.request       │ Violations Service  │ Generate fine bill     │
 * │  mop.renewal.bill.request    │ Renewal Service     │ Generate renewal bill  │
 * │  mop.bill.cancel.request     │ License / Admin Svc │ Cancel bill            │
 * │  mop.export.bill.request     │ Export Service      │ Generate export bill   │
 * └─────────────────────────────────────────────────────────────────────────────┘
 *
 * Each consumer is idempotent: if the bill already exists for the given reference,
 * it logs and skips rather than creating a duplicate.
 *
 * Manual ACK mode is used so a failed message is retried, not lost.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BillingEventConsumer {

    private final IBillService billService;
    private final ObjectMapper objectMapper;

    // ─────────────────────────────────────────────────────────────────────────
    // LICENSE BILL REQUEST
    // From: License Service, when a license application is approved
    // Topic: mop.license.bill.request
    //
    // Expected payload:
    // {
    //   "invId":          67890,
    //   "licNumber":      12345,
    //   "licType":        "M",
    //   "billOwnerName":  "شركة التعدين",
    //   "nationalId":     "1234567890",
    //   "cr":             "1010123456",
    //   "poiType":        "CR",
    //   "billCategory":   "LICENSE_FEE",
    //   "displayLabelAr": "رسوم ترخيص تعدين",
    //   "totalAmount":    5000.00,
    //   "billType":       1,
    //   "referenceType":  3,
    //   "referenceNumber":12345,
    //   "dueDate":        "2026-06-01T00:00:00",
    //   "revenueEntries": [{"accountNo":"1422101","amount":5000.00}]
    // }
    // ─────────────────────────────────────────────────────────────────────────
    @KafkaListener(
            topics        = "#{@kafkaConsumerTopics.licenseBillRequest}",
            groupId       = "mop-billing-service",
            containerFactory = "manualAckKafkaListenerContainerFactory"
    )
//    public void onLicenseBillRequest(ConsumerRecord<String, String> record, Acknowledgment ack) {
//        log.info("[Consumer] License bill request received key={}", record.key());
//        try {
//            BillCreateRequest request = objectMapper.readValue(record.value(), BillCreateRequest.class);
//            request.setBillType(1); // enforce: license = claim
//            billService.createBill(request);
//            ack.acknowledge();
//            log.info("[Consumer] License bill created for licNumber={}", request.getLicNumber());
//        } catch (Exception e) {
//            log.error("[Consumer] Failed to process license bill request key={}: {}",
//                    record.key(), e.getMessage(), e);
//            // Do NOT ack — message will be retried by Kafka
//        }
//    }

    // ─────────────────────────────────────────────────────────────────────────
    // FINE / VIOLATION BILL REQUEST
    // From: Violations Service, when a fine is issued
    // Topic: mop.fine.bill.request
    //
    // Expected payload:
    // {
    //   "invId":          67890,
    //   "licNumber":      12345,
    //   "licType":        "M",
    //   "billOwnerName":  "شركة التعدين",
    //   "nationalId":     "1234567890",
    //   "poiType":        "NI",
    //   "billCategory":   "FINE",
    //   "displayLabelAr": "غرامة مخالفة تعدين",
    //   "totalAmount":    2000.00,
    //   "billType":       2,
    //   "referenceType":  5,
    //   "referenceNumber":789,
    //   "dueDate":        "2026-05-01T00:00:00",
    //   "revenueEntries": [{"accountNo":"1422103","amount":2000.00}]
    // }
    // ─────────────────────────────────────────────────────────────────────────
    @KafkaListener(
            topics        = "#{@kafkaConsumerTopics.fineBillRequest}",
            groupId       = "mop-billing-service",
            containerFactory = "manualAckKafkaListenerContainerFactory"
    )
//    public void onFineBillRequest(ConsumerRecord<String, String> record, Acknowledgment ack) {
//        log.info("[Consumer] Fine bill request received key={}", record.key());
//        try {
//            BillCreateRequest request = objectMapper.readValue(record.value(), BillCreateRequest.class);
//            request.setBillType(2); // enforce: fine
//            billService.createBill(request);
//            ack.acknowledge();
//            log.info("[Consumer] Fine bill created for invId={}", request.getInvId());
//        } catch (Exception e) {
//            log.error("[Consumer] Failed to process fine bill request key={}: {}",
//                    record.key(), e.getMessage(), e);
//        }
//    }

    // ─────────────────────────────────────────────────────────────────────────
    // RENEWAL BILL REQUEST
    // From: License Renewal Service, when renewal is approved
    // Topic: mop.renewal.bill.request
    //
    // Same payload structure as LICENSE BILL REQUEST.
    // billCategory should be "RENEWAL_FEE".
    // ─────────────────────────────────────────────────────────────────────────
    @KafkaListener(
            topics        = "#{@kafkaConsumerTopics.renewalBillRequest}",
            groupId       = "mop-billing-service",
            containerFactory = "manualAckKafkaListenerContainerFactory"
    )
//    public void onRenewalBillRequest(ConsumerRecord<String, String> record, Acknowledgment ack) {
//        log.info("[Consumer] Renewal bill request received key={}", record.key());
//        try {
//            BillCreateRequest request = objectMapper.readValue(record.value(), BillCreateRequest.class);
//            request.setBillType(1);
//            billService.createBill(request);
//            ack.acknowledge();
//        } catch (Exception e) {
//            log.error("[Consumer] Failed to process renewal bill request key={}: {}",
//                    record.key(), e.getMessage(), e);
//        }
//    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXPORT BILL REQUEST
    // From: Export Service, when an export request is approved
    // Topic: mop.export.bill.request
    //
    // Same payload structure. billCategory = "EXPORT_FEE".
    // ─────────────────────────────────────────────────────────────────────────
//    @KafkaListener(
//            topics        = "#{@kafkaConsumerTopics.exportBillRequest}",
//            groupId       = "mop-billing-service",
//            containerFactory = "manualAckKafkaListenerContainerFactory"
//    )
//    public void onExportBillRequest(ConsumerRecord<String, String> record, Acknowledgment ack) {
//        log.info("[Consumer] Export bill request received key={}", record.key());
//        try {
//            BillCreateRequest request = objectMapper.readValue(record.value(), BillCreateRequest.class);
//            request.setBillType(1);
//            billService.createBill(request);
//            ack.acknowledge();
//        } catch (Exception e) {
//            log.error("[Consumer] Failed to process export bill request key={}: {}",
//                    record.key(), e.getMessage(), e);
//        }
//    }

    // ─────────────────────────────────────────────────────────────────────────
    // BILL CANCEL REQUEST
    // From: License Service / Admin Service, when a bill must be voided
    // Topic: mop.bill.cancel.request
    //
    // Expected payload:
    // {
    //   "billNumber":  1000000001,
    //   "cancelReason": "DUPLICATE",
    //   "cancelNote":   "Created in error",
    //   "cancelledBy":  42
    // }
    // ─────────────────────────────────────────────────────────────────────────
    @KafkaListener(
            topics        = "#{@kafkaConsumerTopics.billCancelRequest}",
            groupId       = "mop-billing-service",
            containerFactory = "manualAckKafkaListenerContainerFactory"
    )
    public void onBillCancelRequest(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("[Consumer] Bill cancel request received key={}", record.key());
        try {
            BillCancelRequest request = objectMapper.readValue(record.value(), BillCancelRequest.class);
            billService.cancelBill(request);
            ack.acknowledge();
            log.info("[Consumer] Bill cancelled: billNumber={}", request.getBillNumber());
        } catch (Exception e) {
            log.error("[Consumer] Failed to process bill cancel request key={}: {}",
                    record.key(), e.getMessage(), e);
        }
    }
}
