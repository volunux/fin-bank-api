package com.fintest.testifi.domain.other;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum BankTransactionType {
	
	CREDIT("credit"),
	DEBIT("debit"),
	DEPOSIT("deposit"),
	BANK_FEE("bank-fee"),
	WITHDRAWAL("withdrawal"),
	TRANSFER("transfer");
	
	private String accountTransactionType;
	
	BankTransactionType(String accountTransactionType) {
		this.accountTransactionType = accountTransactionType;
	}
	
	@JsonValue
	public String getValue() {
		return this.accountTransactionType;
	}
	
	@Override
	public String toString() {
		return this.accountTransactionType;
	}
}
