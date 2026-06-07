package com.mop.billing.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * REST request body for POST /api/bills
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BillCreateRequest {

    @NotNull(message = "licNumber is required")
    private Integer licNumber;

    private String licType;

    @NotNull(message = "invId is required")
    private Integer invId;

    @NotBlank(message = "billOwnerName is required")
    @Size(max = 278)
    private String billOwnerName;

    @NotBlank(message = "nationalId is required")
    private String nationalId;

    private String cr;

    /** POI type: NI = National ID, CR = Commercial Register */
    @NotBlank(message = "poiType is required")
//    @Pattern(regexp = "NI|CR", message = "poiType must be NI or CR")
    private String poiType;

    @NotBlank(message = "billCategory is required")
    private String billCategory;

    @NotBlank(message = "displayLabelAr is required")
    @Size(max = 31)
    private String displayLabelAr;

    @NotNull(message = "totalAmount is required")
    @DecimalMin(value = "0.01", message = "totalAmount must be positive")
    private BigDecimal totalAmount;

    @NotNull(message = "dueDate is required")
    private Date dueDate;

    private Date expireDate;          // defaults to dueDate + config months

    private String notes;

    /** 1 = claim, 2 = fine */
    @NotNull(message = "billType is required")
    private Integer billType;

    @NotNull(message = "referenceType is required")
    private Integer referenceType;

    private Integer referenceNumber;

    @NotEmpty(message = "At least one revenue entry is required")
    @Valid
    private List<RevenueEntryRequest> revenueEntries;
}
