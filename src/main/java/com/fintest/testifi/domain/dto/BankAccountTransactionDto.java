package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fintest.testifi.domain.other.BankTransactionType;
import com.fintest.testifi.validator.BankAccountTransactionTypeValidator;

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
	private String accountNumber;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Min(value = 9999, message = "{bankAccount.pin.min}")
	private Long accountPin;
	
	@NotNull(message = "{bankAccount.amount.notNull}")
	@Min(value = 1, message = "{bankAccount.amount.min}")
	private Double amount;
	
	@NotNull(message = "{bankTransaction.transactionType.notNull}")
	@NotBlank(message = "{bankTransaction.transactionType.notBlank}")
	@BankAccountTransactionTypeValidator(enumClass = BankTransactionType.class, message = "{bankAccount.type.enum}")
	private String transactionType;

}
