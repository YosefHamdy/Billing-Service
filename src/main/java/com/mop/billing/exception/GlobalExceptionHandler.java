package com.mop.billing.exception;

import com.mop.billing.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Global exception handler.
 * Converts all exceptions into the standard ApiResponse envelope.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── Domain exceptions ─────────────────────────────────────────────────────

    @ExceptionHandler(BillNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleBillNotFound(BillNotFoundException ex) {
        return ApiResponse.fail(ex.getMessage());
    }

    @ExceptionHandler(BillAlreadyPaidException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleBillAlreadyPaid(BillAlreadyPaidException ex) {
        return ApiResponse.fail(ex.getMessage());
    }

    @ExceptionHandler(TahseelException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiResponse<Void> handleTahseelException(TahseelException ex) {
        log.error("[Tahseel] Gateway error: {}", ex.getMessage());
        return ApiResponse.fail("Tahseel gateway error: " + ex.getStatusCode());
    }

    // ── Validation errors ─────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ApiResponse.fail("Validation failed: " + errors);
    }

    // ── Security exceptions ───────────────────────────────────────────────────

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Void> handleAccessDenied(AccessDeniedException ex) {
        log.warn("[Security] Access denied: {}", ex.getMessage());
        return ApiResponse.fail("Access denied — insufficient permissions");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleAuthException(AuthenticationException ex) {
        return ApiResponse.fail("Authentication required");
    }

    // ── Catch-all ─────────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGeneral(Exception ex) {
        log.error("[GlobalHandler] Unhandled exception", ex);
        return ApiResponse.fail("An internal error occurred");
    }
}
