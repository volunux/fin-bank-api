package com.fintest.testifi.domain.exception;

public class CustomerNotFoundException extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Customer";
	private Object entityId = null;
	
	public CustomerNotFoundException(Object entityId) {
		this.entityId = entityId;
	}	
	
	@Override
	public String getMessage() {
		return String.format("%s with an id %s cannot be found or does not exist in record.", ENTITY_NAME, entityId.toString());
	}

}
