package com.fintest.testifi.domain.other;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum BankTransactionStatus {

	SUCCESS("success"),
	PENDING("pending"),
	FAILED("failed");
	
	private String transactionStatus;
	
	BankTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	@JsonValue
	public String getValue() {
		return this.transactionStatus;
	}
	
	@Override
	public String toString() {
		return this.transactionStatus;
	}

}
