package com.fintest.testifi.domain.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
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
public class CustomerUpdateDto {

	
	@Size(min = 3, max = 35, message = "{customer.fullName.size}")
	private String fullName;
	
	@Email(message = "{customer.emailAddress.email}")
	@Size(min = 5, max = 50, message = "{customer.emailAddress.size}")
	private String emailAddress;
	
	@Size(min = 1, max = 15, message = "{customer.phoneNumber.size}")
	@Pattern(regexp = "^\\+234[0-9]{10}", message ="{customer.phoneNumber.pattern}")
	private String phoneNumber;

	@Size(min = 1, max = 200, message = "{customer.contactAddress.size}")
	private String contactAddress;

	@Size(min = 1, max = 200, message = "{customer.homeAddress.size}")
	private String homeAddress;
	
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Past(message = "{customer.dateOfBirth.past}")
	private Date dateOfBirth;



}
