package com.fintest.testifi.domain.exception;

public class CustomerDuplicateEntityException extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Customer";
	
	@Override
	public String getMessage() {
		return String.format("%s entry already exists in record.", ENTITY_NAME);
	}

}
