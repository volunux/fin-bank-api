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
	
	@NotNull(message = "{customerDto.fullName.notNull}")
	@NotBlank(message = "{customerDto.fullName.notBlank}")
	@Size(min = 3, max = 150, message = "{customerDto.fullName.size}")
	private String fullName;
	
	@NotNull(message = "{customerDto.emailAddress.notNull}")
	@NotBlank(message = "{customerDto.emailAddress.notBlank}")
	@Email(message = "{customerDto.emailAddress.email}")
	@Size(min = 5, max = 50, message = "{customerDto.emailAddress.size}")
	private String emailAddress;
	
	@NotNull(message = "{customerDto.phoneNumber.notNull}")
	@NotBlank(message = "{customerDto.phoneNumber.notBlank}")
	@Size(min = 1, max = 15, message = "{customerDto.phoneNumber.size}")
	private String phoneNumber;

	@NotNull(message = "{customerDto.contactAddress.notNull}")
	@NotBlank(message = "{customerDto.contactAddress.notBlank}")
	@Size(min = 1, max = 200, message = "{customerDto.contactAddress.size}")
	private String contactAddress;

	@NotNull(message = "{customerDto.homeAddress.notNull}")
	@NotBlank(message = "{customerDto.homeAddress.notBlank}")
	@Size(min = 1, max = 200, message = "{customerDto.homeAddress.size}")
	private String homeAddress;
	

	@NotNull(message = "{customerDto.dateOfBirth.notNull}")
	@NotBlank(message = "{customerDto.dateOfBirth.notBlank}")
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Past(message = "{customerDto.dateOfBirth.past}")
	
	private Date dateOfBirth;

}
