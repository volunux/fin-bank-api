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
	private Long id;
	
	@Column(nullable = false, name = "account_number", updatable = false)
	private String accountNumber;
	
	@Column(nullable = false, name = "account_type")
	@Enumerated(value = EnumType.STRING)
	private BankAccountType accountType;

	@Column(nullable = false, name = "account_status")
	@Enumerated(value = EnumType.STRING)
	private BankAccountStatus accountStatus;
	
	@Column(nullable = false, name ="account_pin")
	private String accountPin;

	@Column(nullable = false, name = "balance")
	private Double balance;
	
	@Column(name ="interest_rate")
	private Float interestRate;
	
	@Column(name ="last_time_accessed")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimeAccessed;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "created_on", updatable = false)
	private Date createdOn;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updated_on")
	private Date updatedOn;
	
	@JoinColumn(name = "customer_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JsonIgnoreProperties(value = {"bankAccounts"}, allowSetters = true)
	private Customer customer;
		
	
	@PreUpdate
	public void accountUpdate() {
		this.lastTimeAccessed = new Date();
	}
}
