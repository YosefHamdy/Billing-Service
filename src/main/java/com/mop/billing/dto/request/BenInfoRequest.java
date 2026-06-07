package com.mop.billing.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenInfoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull(message = "benPOI is required")
    private BenPOI benPOI;

    @NotBlank(message = "benName is required")
    private String benName;
}