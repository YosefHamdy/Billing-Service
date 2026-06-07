package com.mop.billing.service.impl;

import com.mop.billing.config.BillProperties;
import com.mop.billing.config.TahseelProperties;
import com.mop.billing.data.entity.*;
import com.mop.billing.data.repository.*;
import com.mop.billing.dto.request.BillCancelRequest;
import com.mop.billing.dto.request.BillCreateRequest;
import com.mop.billing.dto.request.GenerateBillRequest;
import com.mop.billing.dto.request.RevenueEntryRequest;
import com.mop.billing.dto.response.BillResponse;
import com.mop.billing.dto.response.BillStatusResponse;
import com.mop.billing.exception.BillNotFoundException;
import com.mop.billing.exception.BillAlreadyPaidException;
import com.mop.billing.producer.BillingEventProducer;
import com.mop.billing.service.IBillService;
import com.mop.billing.service.ITahseelGatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Business Layer — Bill lifecycle.
 *
 * Orchestrates:
 * 1. Persist bill + revenue entries to DB
 * 2. Dispatch to Tahseel via ITahseelGatewayService
 * 3. Publish Kafka events via BillingEventProducer
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BillService implements IBillService {

    private final BillMasterRepository      billMasterRepo;
    private final SadadWsBillsRepository    sadadWsBillsRepo;
    private final RevenueEntryRepository    revenueEntryRepo;
    private final PayMasterRepository payMasterRepository;
    private final ITahseelGatewayService    tahseelGateway;
    private final BillingEventProducer      eventProducer;
    private final BillProperties            billProps;
    private final TahseelProperties tahseelProps;

    // ── Bill number generation ── mirrors original Oracle sequence logic ──────
    // In production this sequence is managed by the DB; here we derive it.
    private static final long BILL_NO_BASE = 1_000_000_000L;

    // ─────────────────────────────────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────────────────────────────────

  //  @Transactional
    @Override
    public BillResponse generateBill(
            GenerateBillRequest req) throws Exception {

        validateGenerateRequest(req);

        Long billNumber =
                Long.valueOf(generateBillNumber());

        SadadWsBills sadadBill =
                buildSadadBill(req, billNumber);

        sadadWsBillsRepo.save(sadadBill);

        List<RevenueEntry> tahseelRevenueEntries =
                req.getRevenueEntryList()
                        .stream()
                        .map(r -> RevenueEntry.builder()
                                .fitem(r.getFitem())
                                .gfsCode(r.getGfsCode())
                                .amt(r.getAmt())
                                .benAgencyId(r.getBenAgencyId())
                                .build())
                        .toList();

        List<SadadWsDet> details =
                req.getRevenueEntryList()
                        .stream()
                        .map(r -> SadadWsDet.builder()
                                .billNo(billNumber)
                                .accountNo(String.valueOf(r.getGfsCode()))   // or gfsCode depending on DTO
                                .amount(r.getAmt())         // or amt
                                .payIdDetail(r.getFitem()) // or fitem
                                .build())
                        .toList();

        revenueEntryRepo.saveAll(details);

        String tahseelCode =
                tahseelGateway.addBill(
                        sadadBill,
                        tahseelRevenueEntries);

        return BillResponse.builder()
                .billNumber(billNumber)
                .totalAmount(req.getBillAmt())
                .billOwnerName(
                        req.getBenInfo().getBenName())
                .tahseelStatusCode(tahseelCode)
                .build();
    }


    private void validateGenerateRequest(
            GenerateBillRequest req) throws Exception {

        if (("I".equals(req.getBillAction())
                || "U".equals(req.getBillAction()))
                && StringUtils.hasText(
                req.getActionReason())) {

            throw new Exception(
                    "Action reason is not allowed");
        }

        if ("E".equals(req.getBillAction())
                && !StringUtils.hasText(
                req.getActionReason())) {

            throw new Exception(
                    "Action reason is required");
        }

        BigDecimal total =
                req.getRevenueEntryList()
                        .stream()
                        .map(RevenueEntryRequest::getAmt)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (req.getBillAmt()
                .compareTo(total) != 0) {

            throw new Exception(
                    "Revenue total mismatch");
        }

        for (RevenueEntryRequest entry :
                req.getRevenueEntryList()) {

            if (!payMasterRepository.existsByParentAccountNumber(
                    String.valueOf(entry.getFitem()))) {

                throw new Exception("Invalid fitem");
            }
        }
    }


    private SadadWsBills buildSadadBill(
            GenerateBillRequest req,
            Long billNumber) throws ParseException {

        Date now = new Date();

        SadadWsBills bill = new SadadWsBills();

        bill.setBillNo(String.valueOf(billNumber));

        bill.setAgencyId(tahseelProps.getAgencyId());

        bill.setBillAction(req.getBillAction());

        if ("E".equals(req.getBillAction())) {
            bill.setActionReason(req.getActionReason());
        }

        bill.setBillCategory("DMMRS");

        bill.setBillAmount(req.getBillAmt());

        bill.setDueDate(changeToDate(req.getDueDt()));

        bill.setExpireDate(changeToDate(req.getExpDt()));

        bill.setBillRefInfo(req.getBillRefInfo());

        bill.setPoiNum(
                req.getBenInfo()
                        .getBenPOI()
                        .getPoiNum());

        bill.setPoiType(
                req.getBenInfo()
                        .getBenPOI()
                        .getPoiType());

        bill.setBenName(
                req.getBenInfo()
                        .getBenName());

        bill.setCommercialRegAndNationalId(
                req.getBenInfo()
                        .getBenPOI()
                        .getPoiNum());

        bill.setCreatedDate(now);

        return bill;
    }

    private Date changeToDate(String date) throws ParseException, ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
   /* @Override
    @Transactional
    public BillResponse createBill(BillCreateRequest req) {
        log.info("[BillService] Creating bill for invId={} licNo={} amount={}",
                req.getInvId(), req.getLicNumber(), req.getTotalAmount());

        // 1. Generate bill number (DB sequence in production)
        Long billNumber = Long.valueOf(generateBillNumber());

        // 2. Resolve dates
        Date now        = new Date();
        Date dueDate    = req.getDueDate();
        Date expireDate = req.getExpireDate() != null
                ? req.getExpireDate()
                : addMonths(now, billProps.getDefaultExpireMonths());

        // 3. Persist BillMaster
        BillMaster billMaster = BillMaster.builder()
                .billNumber(billNumber)
                .licNumber(req.getLicNumber())
                .licType(req.getLicType())
                .billStatus(0)                      // 0 = UNPAID
                .billType(req.getBillType())
                .billOwnerName(req.getBillOwnerName())
                .nationalId(req.getNationalId())
                .cr(req.getCr())
                .invId(req.getInvId())
                .referenceType(req.getReferenceType())
                .referenceNumber(req.getReferenceNumber())
                .payAmount(req.getTotalAmount())
                .billDateG(now)
                .billDate(new java.text.SimpleDateFormat("yyyy-MM-dd").format(now))
                .active(1)
                .notes(req.getNotes())
                .build();
        billMasterRepo.save(billMaster);

        // 4. Persist RevenueEntry rows (GFS breakdown)
        List<RevenueEntry> revenueEntries = req.getRevenueEntries().stream()
                .map(r -> RevenueEntry.builder()
                        .billNo(billNumber)
                        .accountNo(r.getAccountNo())
                        .amount(r.getAmount())
                        .payIdDetail(r.getPayIdDetail())
                        .build())
                .collect(Collectors.toList());
        revenueEntryRepo.saveAll(revenueEntries);

        // 5. Persist SadadWsBills record (pre-dispatch snapshot)
        SadadWsBills sadadBill = SadadWsBills.builder()
                .billNo(String.valueOf(billNumber))
                .agencyId(null)                     // set from TahseelProperties in gateway
                .billAction("A")
                .billCategory(req.getBillCategory())
                .displayLabelAr(req.getDisplayLabelAr())
                .billAmount(req.getTotalAmount())
                .dueDate(dueDate)
                .expireDate(expireDate)
                .billRefInfo(req.getNationalId())
                .poiNum(req.getNationalId())
                .poiType(req.getPoiType())
                .benName(req.getBillOwnerName())
                .commercialRegAndNationalId(req.getCr() != null ? req.getCr() : req.getNationalId())
                .createdDate(now)
                .build();
        sadadWsBillsRepo.save(sadadBill);

        // 6. Dispatch to Tahseel
       String tahseelCode = tahseelGateway.addBill(sadadBill, revenueEntries);
        log.info("[BillService] Tahseel dispatch result: bill={} code={}", billNumber, tahseelCode);

//        // 7. Publish Kafka event
//        eventProducer.billCreated(billNumber, req.getInvId(), req.getTotalAmount(), tahseelCode);

        return toBillResponse(billMaster
        //        tahseelCode
        );
    }*/

    // ─────────────────────────────────────────────────────────────────────────
    // CANCEL
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void cancelBill(BillCancelRequest req) {
        log.info("[BillService] Cancelling bill={}", req.getBillNumber());

        BillMaster bill = billMasterRepo.findByBillNumber(req.getBillNumber())
                .orElseThrow(() -> new BillNotFoundException(req.getBillNumber()));

        if (bill.isPaid()) {
            throw new BillAlreadyPaidException(req.getBillNumber());
        }

        // Update local record
        bill.setBillStatus(2);  // CANCELLED
        bill.setCanceledDate(new Date());
        bill.setCancelNote(req.getCancelNote());
        bill.setCanceledBy(req.getCancelledBy());
        billMasterRepo.save(bill);

        // Cancel in Tahseel
        String agencyId = sadadWsBillsRepo.findByBillNo(String.valueOf(req.getBillNumber()))
                .map(SadadWsBills::getAgencyId)
                .orElse(null);
        String tahseelCode = tahseelGateway.cancelBill(
                String.valueOf(req.getBillNumber()), agencyId, req.getCancelReason());

        // Publish event
        eventProducer.billCancelled(req.getBillNumber(), req.getCancelReason(), tahseelCode);
        log.info("[BillService] Bill={} cancelled. Tahseel code={}", req.getBillNumber(), tahseelCode);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // QUERIES
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public BillStatusResponse getBillStatus(Long billNumber) {
        BillMaster bill = billMasterRepo.findByBillNumber(billNumber)
                .orElseThrow(() -> new BillNotFoundException(billNumber));

        String tahseelReqId = sadadWsBillsRepo.findByBillNo(String.valueOf(billNumber))
                .map(SadadWsBills::getReqId).orElse(null);

        return BillStatusResponse.builder()
                .billNumber(billNumber)
                .billStatus(bill.getBillStatus())
                .billStatusLabel(statusLabel(bill.getBillStatus()))
                .paidAmount(bill.getPayAmount())
                .payDateG(bill.getPayDateG())
                .tahseelReqId(tahseelReqId)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> getUnpaidBillsByInvestor(Integer invId) {
        return billMasterRepo.findByInvIdAndBillStatus(invId, 0)
                .stream()
                .map(b -> toBillResponse(b
                //        null
                ))
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCHEDULED RETRY — re-send bills not yet confirmed by Tahseel
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    @Scheduled(fixedDelayString = "${bill.retry-interval-ms:600000}")   // every 10 min
    @Transactional
    public void retryPendingTahseelDispatch() {
        List<SadadWsBills> pending = sadadWsBillsRepo.findPendingDispatch();
        if (pending.isEmpty()) return;

        log.info("[BillService] Retrying {} pending Tahseel dispatches", pending.size());
        for (SadadWsBills wb : pending) {
            try {
                List<SadadWsDet> entries = revenueEntryRepo
                        .findByBillNo(Long.parseLong(wb.getBillNo()));
                String code = tahseelGateway.addBill(wb, null);
                if (tahseelGateway.isSuccess(code)) {
                    eventProducer.tahseelRetrySuccess(wb.getBillNo(), code);
                } else {
                    eventProducer.tahseelFailed(wb.getBillNo(), code);
                }
            } catch (Exception e) {
                log.error("[BillService] Retry failed for bill={}", wb.getBillNo(), e);
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Private helpers
    // ─────────────────────────────────────────────────────────────────────────

//    private Long generateBillNumber() {
//        // In production the DB sequence (SADAD_BILLS_MASTER_SEQ) drives this.
//        // Here we derive a timestamp-based number as a safe fallback.
//        long count = billMasterRepo.count();
//        return BILL_NO_BASE + count + 1;
//    }

    private Date incrementDate(Date date, int field, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, value);
        return c.getTime();
    }
    @Transactional(readOnly = true)
    private String generateBillNumber() {
        Date now = new Date();
        Date today = new Date(now.getYear(), now.getMonth(), now.getDate());
        Date tomorrow = incrementDate(today, Calendar.DAY_OF_MONTH, 1);
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		Integer x = sadadWSBillRepository.countByCreatedDateBetweenAndBillAction(today, tomorrow, "I");
        Integer x = billMasterRepo.countByBillDateGBetween(today, tomorrow);

        // Integer x = nicWsBillRepository.countByCreateDateBetweenAndBillAction(today, tomorrow, "I");

        return localDate.getYear() + String.format("%02d", localDate.getMonthValue()) + String.format("%02d", localDate.getDayOfMonth()) + String.format("%04d", x + 1);


    }

    private BillResponse toBillResponse(BillMaster bill
    //                                    String tahseelCode
    ) {
        return BillResponse.builder()
                .billNumber(bill.getBillNumber())
                .licNumber(bill.getLicNumber())
                .licType(bill.getLicType())
                .billOwnerName(bill.getBillOwnerName())
                .totalAmount(bill.getPayAmount())
                .billStatus(bill.getBillStatus())
                .billStatusLabel(statusLabel(bill.getBillStatus()))
                .billType(bill.getBillType())
                .billDateG(bill.getBillDateG())
               // .tahseelStatusCode(tahseelCode)
                .notes(bill.getNotes())
                .build();
    }

    private String statusLabel(Integer status) {
        if (status == null) return "UNKNOWN";
        return switch (status) {
            case 0 -> "UNPAID";
            case 1 -> "PAID";
            case 2 -> "CANCELLED";
            default -> "UNKNOWN";
        };
    }

    private Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
}
