package com.fintest.testifi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.EqualsAndHashCode;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Customer {

	@Column(nullable = false, name = "full_name")
	private String fullName;	
	
	@Column(nullable = false, unique = true, name = "email_address")
	private String emailAddress;
	
	@Column(nullable = false, unique = true, name = "phone_number")
	private String phoneNumber;
	
	@Column(nullable = false, name = "contact_address")
	private String contactAddress;
	
	@Column(nullable = false, name = "home_address")
	private String homeAddress;
	
	@Column(nullable = false)
	private String age;
}
