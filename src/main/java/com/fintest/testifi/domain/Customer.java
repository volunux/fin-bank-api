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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Entity
@Table(name = "bank_customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "full_name")
	private String fullName;	
	
	@Column(nullable = false, unique = true, updatable = false, name = "email_address")
	private String emailAddress;	
		
	@Column(nullable = false, unique = true, name = "phone_number")
	private String phoneNumber;
	
	@Column(nullable = false, name = "contact_address")
	private String contactAddress;
	
	@Column(nullable = false, name = "home_address")
	private String homeAddress;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false, name = "date_of_birth")
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
	private Set<BankAccount> bankAccounts = new HashSet<BankAccount>();
}
