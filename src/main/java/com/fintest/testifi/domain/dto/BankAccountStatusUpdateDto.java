package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.domain.other.BankAccountType;
import com.fintest.testifi.validator.BankAccountStatusValidator;

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
	@Size(min = 1, max = 10, message = "{bankAccount.accountNumber.size}")
	private String accountNumber;

	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.min}")
	private Long accountPin;
	
	@NotNull(message = "{bankAccount.status.notNull}")
	@NotBlank(message = "{bankAccount.status.notBlank}")
	@BankAccountStatusValidator(enumClass = BankAccountStatus.class, message = "{bankAccount.status.enum}")
	private String accountStatus;
}
