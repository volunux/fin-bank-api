package com.fintest.testifi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.domain.other.BankAccountType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"customer"})
@ToString(exclude = {"customer"})
@Entity
@Table(name = "bank_account" , indexes = {
		@Index(columnList = "account_number", name ="account_number_idx", unique = true)
})
public class BankAccount {
	
	public BankAccount() {
		long randomNumber = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		String preAccountNumber = "FE" + randomNumber;
		this.accountNumber = preAccountNumber;
		this.lastTimeAccessed = new Date();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "The unique generated ID of the bank account",
	    name = "id",
	    required = false )
	private Long id;
	
	@Column(nullable = false, name = "account_number", updatable = false)
	@Schema(description = "The auto generated account number assigned to a bank account when creating a customer or additional bank account",
	    name = "accountNumber",
	    maxLength = 12,
	    minLength = 1,
	    required = true,
	    example = "FE3982028311" )
	private String accountNumber;
	
	@Column(nullable = false, name = "account_type")
	@Enumerated(value = EnumType.STRING)
	@Schema(description = "The type of bank account customer wants to open",
	    name = "accountType",
	    required = true,
	    example = "saving" )
	private BankAccountType accountType;

	@Column(nullable = false, name = "account_status")
	@Enumerated(value = EnumType.STRING)
	@Schema(description = "The status of the bank account",
	    name = "accountStatus",
	    required = false,
	    example = "active" )
	private BankAccountStatus accountStatus;
	
	@Column(nullable = false, name ="account_pin")
	@Schema(description = "Secure pin that is used to perform bank transactions",
	    name = "accountPin",
	    required = true,
	    example = "1992",
	    minimum = "999",
	    maximum = "9999" )
	private String accountPin;

	@Column(nullable = false, name = "balance")
	@Schema(description = "The bank account current balance",
	    name = "balance",
	    required = true,
	    example = "10000")
	private Double balance;
	
	@Column(name ="interest_rate")
	@Schema(description = "The interest rate assigned to the bank account",
	    name = "interestRate",
	    required = false,
	    example = "0.8")
	private Float interestRate;
	
	@Column(name ="last_time_accessed")
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "The last time a transaction was performed on the bank account",
	    name = "lastTimeAccessed",
	    required = false )
	private Date lastTimeAccessed;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "created_on", updatable = false)
	@Schema(description = "The date the bank account was created",
	    name = "createdOn",
	    required = false )
	private Date createdOn;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updated_on")
	@Schema(description = "The date the bank account was updated",
	    name = "updatedOn",
	    required = false )
	private Date updatedOn;
	
	@JoinColumn(name = "customer_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JsonIgnoreProperties(value = {"bankAccounts"}, allowSetters = true)
	@Schema(description = "The owner or customer of the bank account",
	    name = "customer",
	    required = false )
	private Customer customer;
		
	
	@PreUpdate
	public void accountUpdate() {
		this.lastTimeAccessed = new Date();
	}
}
