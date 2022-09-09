package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fintest.testifi.domain.other.BankAccountType;
import com.fintest.testifi.validator.BankAccountTypeValidator;

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
	private String accountNumber;
	
	@NotNull(message = "{bankAccount.type.notNull}")
	@NotBlank(message = "{bankAccount.type.notBlank}")
	@BankAccountTypeValidator(enumClass = BankAccountType.class, message = "{bankAccount.type.enum}")
	private String accountType;
		
	@NotNull(message = "{bankAccount.initialDeposit.notNull}")
	@Min(value = 100, message = "{bankAccount.initialDeposit.min}")
	private Long initialDeposit;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Min(value = 9999, message = "{bankAccount.pin.min}")
	private Long accountPin;

}
