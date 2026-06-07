package com.mop.billing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/** REST request body for DELETE /api/bills/{billNumber}/cancel */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BillCancelRequest {

    @NotNull(message = "billNumber is required")
    private Long billNumber;

    @NotBlank(message = "cancelReason is required")
    private String cancelReason;

    private String cancelNote;

    private Integer cancelledBy;
}
