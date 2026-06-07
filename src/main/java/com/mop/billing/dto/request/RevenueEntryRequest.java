package com.mop.billing.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * A single GFS-code revenue breakdown line inside BillCreateRequest.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RevenueEntryRequest {
    @NotNull(message = "fitem is required")
    private Integer fitem;

    @NotBlank(message = "gfsCode is required")
    private String gfsCode;

    @NotNull(message = "amt is required")
    @DecimalMin(value = "0.01")
    private BigDecimal amt;

    private String benAgencyId;
}
