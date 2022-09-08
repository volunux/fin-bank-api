package com.fintest.testifi.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@Size(min = 1, max = 10, message = "{bankAccount.accountNumber.size}")
	private String accountNumber;

	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.min}")
	private Long accountPin;
}
