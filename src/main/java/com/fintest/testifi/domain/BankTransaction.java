package com.fintest.testifi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fintest.testifi.domain.other.BankTransactionStatus;
import com.fintest.testifi.domain.other.BankTransactionType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "bank_transaction")
public class BankTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(nullable = false, updatable = false)
	private Double amount;
	
	@JoinColumn(name = "initiator_account", nullable = true, updatable = false)
	@ManyToOne
	private BankAccount initiatorBankAccount;
	
	@JoinColumn(name = "recipient_account", nullable = true, updatable = false)
	@ManyToOne
	private BankAccount recipientBankAccount;
	
	@Column(nullable = true, name = "description", updatable = false)
	private String description;
	
	@Column(nullable = false, name ="transaction_type", updatable = false)
	@Enumerated(value = EnumType.STRING)
	private BankTransactionType transactionType;
	
	@Column(nullable = false, name ="transaction_status", updatable = false)
	@Enumerated(value = EnumType.STRING)
	private BankTransactionStatus transactionStatus;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "created_on", updatable = false)
	private Date createdOn;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updated_on")
	private Date updatedOn;

}
