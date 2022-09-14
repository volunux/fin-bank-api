package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fintest.testifi.domain.other.BankTransactionType;
import com.fintest.testifi.validator.BankAccountTransactionTypeValidator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BankAccountTransactionDto {

	@NotNull(message = "{bankAccount.accountNumber.notNull}")
	@NotBlank(message = "{bankAccount.accountNumber.notBlank}")
	@Size(min = 1, max = 12, message = "{bankAccount.accountNumber.size}")
	@Schema(description = "The existing bank account's number to be used in the bank transaction",
	    name = "accountNumber",
	    maxLength = 12,
	    minLength = 1,
	    required = true,
	    example = "FE2837201820" )
	private String accountNumber;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.max}")
	@Schema(description = "Secure pin that is used to perform bank transactions",
	    name = "accountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private Long accountPin;
	
	@NotNull(message = "{bankTransaction.amount.notNull}")
	@Min(value = 1, message = "{bankTransaction.amount.min}")
	@Schema(description = "The amount of the transaction",
	    name = "amount",
	    required = true,
	    example = "10000")
	private Double amount;
	
	@NotNull(message = "{bankTransaction.description.notNull}")
	@NotBlank(message = "{bankTransaction.description.notBlank}")
	@Size(min = 1, max = 200, message = "{bankTransaction.description.size}")
	@Schema(description = "The summary or description of transaction that will be performed on the bank account",
	    name = "description",
	    required = true,
	    example = "money withdrawal for school fees")
	private String description;
	
	@NotNull(message = "{bankTransaction.type.notNull}")
	@NotBlank(message = "{bankTransaction.type.notBlank}")
	@BankAccountTransactionTypeValidator(enumClass = BankTransactionType.class, message = "{bankTransaction.type.enum}")
	@Schema(description = "The type of transaction that will be performed on the bank account like debit, credit, withdrawal or transfer",
	    name = "transactionType",
	    required = true,
	    example = "withdrawal")
	private String transactionType;

}
