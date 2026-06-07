package com.mop.billing.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateBillRequest {

    @NotBlank(message = "serviceProviderID is required")
    private String serviceProviderID;

    /**
     * I = Insert
     * U = Update
     * E = Expire / Cancel
     */
    @NotBlank(message = "billAction is required")
    private String billAction;

    /**
     * Required only when billAction = E
     */
    private String actionReason;

    @NotNull(message = "billAmt is required")
    @DecimalMin(value = "0.01", message = "billAmt must be positive")
    private BigDecimal billAmt;

    /**
     * Format expected by source system
     * Example: 2026-06-07
     */
    @NotBlank(message = "dueDt is required")
    private String dueDt;

    /**
     * Format expected by source system
     * Example: 2026-12-31
     */
    @NotBlank(message = "expDt is required")
    private String expDt;

    @NotBlank(message = "billRefInfo is required")
    private String billRefInfo;

    @Valid
    @NotNull(message = "benInfo is required")
    private BenInfoRequest benInfo;

    @Valid
    @NotEmpty(message = "At least one revenue entry is required")
    private List<RevenueEntryRequest> revenueEntryList;
}