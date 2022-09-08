package com.fintest.testifi.domain.exception;

public class BankAccountDuplicateEntityException extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Bank Account";
	
	@Override
	public String getMessage() {
		return String.format("%s entry already exists in record.", ENTITY_NAME);
	}

}
