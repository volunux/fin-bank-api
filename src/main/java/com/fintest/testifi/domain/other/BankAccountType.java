package com.fintest.testifi.domain.other;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum BankAccountType {

	SAVING("saving"),
	CURRENT("current"),
	CHECKING("checking");
	
	private String accountType;
	
	BankAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@JsonValue
	public String getValue() {
		return this.accountType;
	}
	
	@Override
	public String toString() {
		return this.accountType;
	}

}
