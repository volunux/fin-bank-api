package com.fintest.testifi.domain.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fintest.testifi.domain.other.BankAccountType;
import com.fintest.testifi.validator.BankAccountMinimumAgeValidator;
import com.fintest.testifi.validator.BankAccountTypeValidator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustomerDto {
	
	@NotNull(message = "{customer.fullName.notNull}")
	@NotBlank(message = "{customer.fullName.notBlank}")
	@Size(min = 3, max = 35, message = "{customer.fullName.size}")
	private String fullName;
	
	@NotNull(message = "{customer.emailAddress.notNull}")
	@NotBlank(message = "{customer.emailAddress.notBlank}")	
	@Email(message = "{customer.emailAddress.pattern}")
	@Size(min = 5, max = 50, message = "{customer.emailAddress.size}")
	private String emailAddress;
	
	@NotNull(message = "{customer.phoneNumber.notNull}")
	@NotBlank(message = "{customer.phoneNumber.notBlank}")
	@Size(min = 1, max = 15, message = "{customer.phoneNumber.size}")
	@Pattern(regexp = "^\\+234[0-9]{10}", message ="{customer.phoneNumber.pattern}")
	private String phoneNumber;

	@NotNull(message = "{customer.contactAddress.notNull}")
	@NotBlank(message = "{customer.contactAddress.notBlank}")
	@Size(min = 1, max = 200, message = "{customer.contactAddress.size}")
	private String contactAddress;

	@NotNull(message = "{customer.homeAddress.notNull}")
	@NotBlank(message = "{customer.homeAddress.notBlank}")
	@Size(min = 1, max = 200, message = "{customer.homeAddress.size}")
	private String homeAddress;
	
	@NotNull(message = "{customer.dateOfBirth.notNull}")
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Past(message = "{customer.dateOfBirth.past}")
	@BankAccountMinimumAgeValidator(age = 18, message = "{customer.dateOfBirth.minimumAge}")
	private Date dateOfBirth;

	@Min(value = 0, message = "{bankAccount.initialDeposit.min}")
	private Double initialDeposit;
	
	@NotNull(message = "{bankAccount.type.notNull}")
	@NotBlank(message = "{bankAccount.type.notBlank}")
	@BankAccountTypeValidator(enumClass = BankAccountType.class, message = "{bankAccount.type.enum}")
	private String accountType;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Min(value = 9999, message = "{bankAccount.pin.min}")
	private Long accountPin;

}
