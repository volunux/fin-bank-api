package com.fintest.testifi.domain.exception;

public class BankAccountPinException extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Bank Account";
	private Object accountPin = null;
	private Object accountNumber = null;
	
	public BankAccountPinException(Object accountNumber, Object accountPin) {
		this.accountNumber = accountNumber;
		this.accountPin = accountPin;
	}	
	
	@Override
	public String getMessage() {
		return String.format("%s account number (%s)'s pin %s is invalid or incorrect.", 
				ENTITY_NAME, accountNumber.toString(), accountPin.toString());
	}

}
