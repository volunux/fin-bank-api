package com.fintest.testifi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fintest.testifi.domain.other.BankTransactionType;

public class BankTransaction {
	
	@Id
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column
	private Long amount;
	

	private BankAccount account;
	
	private String description;
	
	
	private BankTransactionType transactionType;

}
