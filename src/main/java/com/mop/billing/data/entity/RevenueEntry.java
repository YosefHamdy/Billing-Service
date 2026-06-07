/**
 *
 */
package com.mop.billing.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @author Khalil
 *
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueEntry implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(message = "fitem should not be null")
	private Integer fitem;
	@NotBlank(message = "gfsCode should not be blank")
	@NotNull(message = "gfsCode should not be null")
	private String gfsCode;

	@NotNull(message = "amt should not be null")
	//@JsonFormat(shape=JsonFormat.Shape.STRING)
	private BigDecimal amt;
	private String benAgencyId;
}
