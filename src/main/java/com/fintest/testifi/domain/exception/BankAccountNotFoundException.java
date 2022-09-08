package com.fintest.testifi.domain.exception;

public class BankAccountNotFoundException extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Bank Account";
	private Object entityId = null;
	
	public BankAccountNotFoundException(Object entityId) {
		this.entityId = entityId;
	}	
	
	@Override
	public String getMessage() {
		return String.format("%s with an id %s cannot be found or does not exist in record.", ENTITY_NAME, entityId.toString());
	}

}
