package com.fintest.testifi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fintest.testifi.domain.other.BankType;

public class BankAccount {

	@Id
	private Long id;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "bank_type")
	@Enumerated(value = EnumType.STRING)
	private BankType bankType;
	
	@Column	
	private Long balance;
	
	@Column(name ="interest_rate")
	private Long interestRate;
	
	@Column(name ="last_time_accessed")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimeAccessed;
		
}
