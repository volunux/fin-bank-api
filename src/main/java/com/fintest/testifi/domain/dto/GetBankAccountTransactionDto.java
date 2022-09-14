package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBankAccountTransactionDto {

	@NotNull(message = "{bankAccount.accountNumber.notNull}")
	@NotBlank(message = "{bankAccount.accountNumber.notBlank}")
	@Size(min = 1, max = 12, message = "{bankAccount.accountNumber.size}")
	@Schema(description = "The bank account's number to be used to retrieve associated transactions",
	    name = "accountNumber",
	    maxLength = 12,
	    minLength = 1,
	    required = true,
	    example = "FE2837201820" )
	private String accountNumber;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.max}")
	@Schema(description = "The bank account's secured secret pin to be used to retrieve associated transactions",
	    name = "accountPin",
	    minimum = "999",
	    maximum = "9999",
	    required = true,
	    example = "1923" )
	private Long accountPin;

}
