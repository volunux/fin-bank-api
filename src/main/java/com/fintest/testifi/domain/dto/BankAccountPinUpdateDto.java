package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BankAccountPinUpdateDto {

	@NotNull(message = "{bankAccount.accountNumber.notNull}")
	@NotBlank(message = "{bankAccount.accountNumber.notBlank}")
	@Size(min = 1, max = 12, message = "{bankAccount.accountNumber.size}")
	@Schema(description = "The existing bank account's number to be used during the pin update",
	    name = "accountNumber",
	    maxLength = 12,
	    minLength = 1,
	    required = true,
	    example = "FE2837201820" )
	private String accountNumber;

	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.max}")
	@Schema(description = "Secure pin that is used to verify the validity of existing pin before changing it",
	    name = "accountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private Long accountPin;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.max}")
	@Schema(description = "Secure pin that is assigned to the account during pin change",
	    name = "newAccountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private Long newAccountPin;
}
