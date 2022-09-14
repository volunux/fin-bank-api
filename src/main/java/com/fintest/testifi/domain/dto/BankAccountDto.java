package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fintest.testifi.domain.other.BankAccountType;
import com.fintest.testifi.validator.BankAccountTypeValidator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BankAccountDto {

	@NotNull(message = "{bankAccount.accountNumber.notNull}")
	@NotBlank(message = "{bankAccount.accountNumber.notBlank}")
	@Size(min = 1, max = 12, message = "{bankAccount.accountNumber.size}")
	@Schema(description = "The existing bank account's number to be used in the process",
	    name = "existingAccountNumber",
	    maxLength = 12,
	    minLength = 1,
	    required = true,
	    example = "FE2837201820" )
	private String existingAccountNumber;
	
	@NotNull(message = "{bankAccount.type.notNull}")
	@NotBlank(message = "{bankAccount.type.notBlank}")
	@BankAccountTypeValidator(enumClass = BankAccountType.class, message = "{bankAccount.type.enum}")
	@Schema(description = "The type of bank account to be used in the process",
	    name = "accountType",
	    required = true,
	    example = "saving" )
	private String accountType;
		
	@NotNull(message = "{bankAccount.initialDeposit.notNull}")
	@Min(value = 100, message = "{bankAccount.initialDeposit.min}")
	@Schema(description = "The bank account initial deposit when creating it",
	    name = "initialDeposit",
	    required = true,
	    example = "10000" )
	private Double initialDeposit;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.max}")
	@Schema(description = "Secure pin that is used to perform bank transactions",
	    name = "existingAccountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private Long existingAccountPin;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.max}")
	@Schema(description = "Secure pin that is assigned to the bank account after creating it",
	    name = "newAccountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private Long newAccountPin;

}
