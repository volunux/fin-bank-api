package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.validator.BankAccountStatusValidator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BankAccountStatusUpdateDto {

	@NotNull(message = "{bankAccount.accountNumber.notNull}")
	@NotBlank(message = "{bankAccount.accountNumber.notBlank}")
	@Size(min = 1, max = 12, message = "{bankAccount.accountNumber.size}")
	@Schema(description = "The existing bank account's number to be used during the bank status update",
	    name = "accountNumber",
	    maxLength = 12,
	    minLength = 1,
	    required = true,
	    example = "FE2837201820" )
	private String accountNumber;

	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.min}")
	@Schema(description = "Secure pin that is used in the transaction",
	    name = "newAccountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private Long accountPin;

	@NotNull(message = "{bankAccount.status.notNull}")
	@NotBlank(message = "{bankAccount.status.notBlank}")
	@BankAccountStatusValidator(enumClass = BankAccountStatus.class, message = "{bankAccount.status.enum}")
	@Schema(description = "The new status to be assigned to the bank account",
	    name = "accountStatus",
	    required = false,
	    example = "active" )
	private String accountStatus;
}
