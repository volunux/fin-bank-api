package com.fintest.testifi.domain.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
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

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description ="The customer's full name",
		name = "fullName",
		required = true,
		minLength = 1,
		maxLength = 150,
	    example = "David Bareth" )
	private String fullName;
	
	@NotNull(message = "{customer.emailAddress.notNull}")
	@NotBlank(message = "{customer.emailAddress.notBlank}")	
	@Email(message = "{customer.emailAddress.pattern}")
	@Size(min = 5, max = 50, message = "{customer.emailAddress.size}")
	@Schema(description ="The customer's email address",
		name = "emailAddress",
		required = true,
		minLength = 3,
		maxLength = 50,
	    example = "davidbareth@gmail.com" )
	private String emailAddress;
	
	@NotNull(message = "{customer.phoneNumber.notNull}")
	@NotBlank(message = "{customer.phoneNumber.notBlank}")
	@Size(min = 1, max = 15, message = "{customer.phoneNumber.size}")
	@Pattern(regexp = "^\\+234[0-9]{10}", message ="{customer.phoneNumber.pattern}")
	@Schema(description ="The customer's phone number",
		name = "phoneNumber",
		required = true,
		minLength = 1,
		maxLength = 15,
	    example = "+2349022035588" )
	private String phoneNumber;

	@NotNull(message = "{customer.homeAddress.notNull}")
	@NotBlank(message = "{customer.homeAddress.notBlank}")
	@Size(min = 1, max = 200, message = "{customer.homeAddress.size}")
	@Schema(description = "The customer's address",
		name ="homeAddress",
		required = true,
		minLength = 1,
		maxLength = 200,
		example = "London, United Kingdom")
	private String homeAddress;
	
	@NotNull(message = "{customer.dateOfBirth.notNull}")
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Past(message = "{customer.dateOfBirth.past}")
	@BankAccountMinimumAgeValidator(age = 18, message = "{customer.dateOfBirth.minimumAge}")
	@Schema(description = "The customer's date of birth",
		name ="dateOfBirth",
		required = true,
		example = "25-07-1998")
	private Date dateOfBirth;

	@NotNull(message = "{bankAccount.initialDeposit.notNull}")
	@Min(value = 100, message = "{bankAccount.initialDeposit.min}")
	@Schema(description = "The bank account initial deposit when new customer bank account",
	    name = "initialDeposit",
	    required = true,
	    example = "10000" )
	private Double initialDeposit;

	@NotNull(message = "{bankAccount.type.notNull}")
	@NotBlank(message = "{bankAccount.type.notBlank}")
	@BankAccountTypeValidator(enumClass = BankAccountType.class, message = "{bankAccount.type.enum}")
	@Schema(description = "The type of bank account to be used when creating new bank account",
	    name = "accountType",
	    required = true,
	    example = "saving" )
	private String accountType;
	
	@NotNull(message = "{bankAccount.pin.notNull}")
	@Min(value = 999, message = "{bankAccount.pin.min}")
	@Max(value = 9999, message = "{bankAccount.pin.max}")
	@Schema(description = "Secure pin that is assigned to the bank account after creating it",
	    name = "accountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private Long accountPin;

}
