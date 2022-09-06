package com.fintest.testifi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "customer")
public class Customer {
	
	@Id
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
}
