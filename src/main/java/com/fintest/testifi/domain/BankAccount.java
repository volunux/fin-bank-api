package com.fintest.testifi.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.domain.other.BankAccountType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Entity
@Table(name = "bank_account" , indexes = {
		@Index(columnList = "account_number", name ="account_number_idx", unique = true)
})
public class BankAccount {
	
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
	@ManyToOne(fetch = FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	private Customer customer;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "initiatorAccount", cascade = CascadeType.ALL)
	private Set<BankTransaction> bankTransactions = new HashSet<BankTransaction>();
	
	@PrePersist
	public void createAccountNumber() {
		long randomNumber = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		String preAccountNumber = "FE" + randomNumber;
		this.accountNumber = preAccountNumber;
		this.lastTimeAccessed = new Date();
	}
	
	@PreUpdate
	public void accountUpdate() {
		this.lastTimeAccessed = new Date();
	}
}
