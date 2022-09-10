package com.fintest.testifi.domain.exception;

public class BankAccountLockedException extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Bank Account";
	private Object entityId = null;
	
	public BankAccountLockedException(Object entityId) {
		this.entityId = entityId;
	}	
	
	@Override
	public String getMessage() {
		return String.format("%s with an id %s is inactive or disabled and cannot perform transactions.", ENTITY_NAME, entityId.toString());
	}

}
