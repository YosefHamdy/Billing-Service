package com.mop.billing.controller;

import com.mop.billing.dto.request.BillCancelRequest;
import com.mop.billing.dto.request.BillCreateRequest;
import com.mop.billing.dto.request.GenerateBillRequest;
import com.mop.billing.dto.response.ApiResponse;
import com.mop.billing.dto.response.BillResponse;
import com.mop.billing.dto.response.BillStatusResponse;
import com.mop.billing.service.IBillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller Layer — Bill management REST API.
 *
 * All endpoints require a valid JWT Bearer token.
 * Role-based access:
 *   POST   /api/bills                  → ROLE_EMPLOYEE (internal users who issue bills)
 *   DELETE /api/bills/{id}/cancel      → ROLE_EMPLOYEE
 *   GET    /api/bills/{id}/status      → ROLE_EMPLOYEE, ROLE_INVESTOR
 *   GET    /api/bills/investor/{invId} → ROLE_EMPLOYEE, ROLE_INVESTOR
 */
@Slf4j
@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final IBillService billService;

    // ─────────────────────────────────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Generate a new bill and push it to Tahseel.
     *
     * POST /api/bills
     * Authorization: Bearer {jwt}
     * Roles: ROLE_EMPLOYEE
     *
     * Body: BillCreateRequest (JSON)
     * Returns: BillResponse with billNumber and Tahseel status code
     */
   /* @PostMapping
    public ResponseEntity<ApiResponse<BillResponse>> createBill(
            @Valid @RequestBody BillCreateRequest request) {

        log.info("[BillController] POST /api/bills invId={} amount={}",
                request.getInvId(), request.getTotalAmount());

        BillResponse bill = billService.createBill(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Bill created successfully", bill));
    }*/

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<BillResponse>> generateBill(
            @Valid @RequestBody GenerateBillRequest request) throws Exception {

        BillResponse response =
                billService.generateBill(request);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Bill generated successfully",
                        response));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CANCEL
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Cancel a bill — locally and in Tahseel.
     *
     * DELETE /api/bills/{billNumber}/cancel
     * Authorization: Bearer {jwt}
     * Roles: ROLE_EMPLOYEE
     */
    @DeleteMapping("/{billNumber}/cancel")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public ResponseEntity<ApiResponse<Void>> cancelBill(
            @PathVariable Long billNumber,
            @Valid @RequestBody BillCancelRequest request) {

        request.setBillNumber(billNumber);
        log.info("[BillController] DELETE /api/bills/{}/cancel", billNumber);

        billService.cancelBill(request);
        return ResponseEntity.ok(ApiResponse.ok("Bill cancelled successfully", null));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // QUERIES
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Get bill payment status.
     *
     * GET /api/bills/{billNumber}/status
     * Authorization: Bearer {jwt}
     * Roles: ROLE_EMPLOYEE, ROLE_INVESTOR
     */
    @GetMapping("/{billNumber}/status")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','INVESTOR')")
    public ResponseEntity<ApiResponse<BillStatusResponse>> getBillStatus(
            @PathVariable Long billNumber) {

        BillStatusResponse status = billService.getBillStatus(billNumber);
        return ResponseEntity.ok(ApiResponse.ok(status));
    }

    /**
     * Get all unpaid bills for an investor.
     *
     * GET /api/bills/investor/{invId}/unpaid
     * Authorization: Bearer {jwt}
     * Roles: ROLE_EMPLOYEE, ROLE_INVESTOR
     */
    @GetMapping("/investor/{invId}/unpaid")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','INVESTOR')")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getUnpaidBillsByInvestor(
            @PathVariable Integer invId) {

        List<BillResponse> bills = billService.getUnpaidBillsByInvestor(invId);
        return ResponseEntity.ok(ApiResponse.ok(bills));
    }

    /**
     * Manually trigger the Tahseel retry job (admin only).
     *
     * POST /api/bills/admin/retry-tahseel
     * Authorization: Bearer {jwt}
     * Roles: ROLE_ADMIN
     */
    @PostMapping("/admin/retry-tahseel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> retryTahseelDispatch() {
        log.info("[BillController] Manual Tahseel retry triggered");
        billService.retryPendingTahseelDispatch();
        return ResponseEntity.ok(ApiResponse.ok("Tahseel retry dispatch completed", null));
    }
}
