package com.fintest.testifi.domain.other;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum BankAccountStatus {

	ACTIVE("active"),
	INACTIVE("inactive"),
	DISABLED("disabled");
	
	private String accountStatus;
	
	BankAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	@JsonValue
	public String getValue() {
		return this.accountStatus;
	}

}
