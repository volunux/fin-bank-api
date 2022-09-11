package com.fintest.testifi.domain.exception;

public class InvalidBankTransferTransaction extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Bank Transaction";

	@Override
	public String getMessage() {
		return String.format("The bank transaction you are trying to perform is not a TRANSFER and is therefore not valid.");
	}

}
