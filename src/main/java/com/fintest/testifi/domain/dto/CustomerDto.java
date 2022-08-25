package com.fintest.testifi.domain.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@Size(min = 3, max = 150, message = "{customer.fullName.size}")
	private String fullName;
	
	@NotNull(message = "{customer.emailAddress.notNull}")
	@NotBlank(message = "{customer.emailAddress.notBlank}")
	@Email(message = "{customer.emailAddress.email}")
	@Size(min = 5, max = 50, message = "{customer.emailAddress.size}")
	private String emailAddress;
	
	@NotNull(message = "{customer.phoneNumber.notNull}")
	@NotBlank(message = "{customer.phoneNumber.notBlank}")
	@Size(min = 1, max = 15, message = "{customer.phoneNumber.size}")
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
	private Date dateOfBirth;

}
