/**
 *
 */
package com.mop.billing.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Khalil
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BenPOI implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "poiNum should not be blank")
	@NotNull(message = "poiNum should not be null")
	private String poiNum;

	@NotBlank(message = "poiType should not be blank")
	@NotNull(message = "poiType should not be null")
	private String poiType;
}
