package com.fintest.testifi.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"bankAccounts"})
@ToString
@Entity
@Table(name = "bank_customer", indexes = {
		@Index(columnList = "email_address", name ="email_address_idx", unique = true)
})
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "The unique generated ID of the customer",
     name = "id",
     required = false )
	private Long id;

	@Column(nullable = false, name = "full_name")
	@Schema(description ="The customer's full name",
		name = "fullName",
		required = true,
		minLength = 1,
		maxLength = 150,
	    example = "David Bareth" )
	private String fullName;
	
	@Column(nullable = false, updatable = true, name = "email_address")
	@Schema(description ="The customer's email address",
		name = "emailAddress",
		required = true,
		minLength = 3,
		maxLength = 50,
	    example = "davidbareth@gmail.com" )
	private String emailAddress;

	@Column(nullable = false, unique = true, name = "phone_number")
	@Schema(description ="The customer's phone number",
		name = "phoneNumber",
		required = true,
		minLength = 1,
		maxLength = 15,
	    example = "+2349022035588" )
	private String phoneNumber;
		
	@Column(nullable = false, name = "home_address")
	@Schema(description = "The customer's address",
		name ="homeAddress",
		required = true,
		minLength = 1,
		maxLength = 200,
		example = "London, United Kingdom")
	private String homeAddress;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false, updatable = false, name = "date_of_birth")
	@Schema(description = "The customer's date of birth",
		name ="dateOfBirth",
		required = true,
		example = "25-07-1998")
	private Date dateofBirth;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "created_on", updatable = false)
	private Date createdOn;
	
	@UpdateTimestamp	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updated_on")
	private Date updatedOn;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"customer"}, allowSetters = true)
	@JsonManagedReference
	private Set<BankAccount> bankAccounts = new HashSet<BankAccount>();
}
