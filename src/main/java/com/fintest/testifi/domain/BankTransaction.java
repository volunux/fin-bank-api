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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fintest.testifi.domain.other.BankTransactionStatus;
import com.fintest.testifi.domain.other.BankTransactionType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"initiatorAccount", "recipientAccount"})
@ToString(exclude = {"initiatorAccount", "recipientAccount"})
@Entity
@Table(name = "bank_transaction")
public class BankTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "The unique generated ID of the bank transaction",
	    name = "id",
	    required = false )
	private Long id;
		
	@Column(nullable = false, updatable = false)
	@Schema(description = "The amount of the transaction",
	    name = "amount",
	    required = true,
	    example = "385")
	private Double amount;
	
	@JoinColumn(name = "initiator_account", nullable = true, updatable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"bankTransactions", "customer"}, allowSetters = true)
	@Schema(description = "The initiator's account number of the bank account transaction",
	    name = "initiatorAccount",
	    required = true,
	    example = "FE2930283712")
	private BankAccount initiatorAccount;
	
	@JoinColumn(name = "recipient_account", nullable = true, updatable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"bankTransactions", "customer"}, allowSetters = true)
	@Schema(description = "The recipient bank account number in case the type of transaction is a money transfer",
	    name = "recipientAccount",
	    required = false,
	    example = "FE2930283847")
	private BankAccount recipientAccount;
	
	@Column(nullable = true, name = "description", updatable = false)
	@Schema(description = "The summary or description of transaction that will be performed on the bank account",
	    name = "description",
	    required = true )
	private String description;
	
	@Column(nullable = false, name ="transaction_type", updatable = false)
	@Enumerated(value = EnumType.STRING)
	@Schema(description = "The type of transaction that will be performed on the bank account like debit, credit, withdrawal or transfer",
	    name = "transactionType",
	    required = true )
	private BankTransactionType transactionType;
	
	@Column(nullable = false, name ="transaction_status", updatable = false)
	@Enumerated(value = EnumType.STRING)
	@Schema(description = "The status of the transaction that was perform like withdrawal or transfer or deposit",
	    name = "transactionStatus",
	    required = false,
	    example = "pending")
	private BankTransactionStatus transactionStatus;
	
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

}
