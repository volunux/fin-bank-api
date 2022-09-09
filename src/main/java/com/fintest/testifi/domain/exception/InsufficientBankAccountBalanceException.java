package com.fintest.testifi.domain.exception;

public class InsufficientBankAccountBalanceException extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Bank Account";
	private Object entityId = null;
	
	public InsufficientBankAccountBalanceException(Object entityId) {
		this.entityId = entityId;
	}	
	
	@Override
	public String getMessage() {
		return String.format("%s with an id %s has an insufficient balance to complete the transaction.", ENTITY_NAME, entityId.toString());
	}

}
