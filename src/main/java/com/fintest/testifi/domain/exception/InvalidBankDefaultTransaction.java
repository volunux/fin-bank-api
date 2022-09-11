package com.fintest.testifi.domain.exception;

public class InvalidBankDefaultTransaction extends FintestException {

	private static final long serialVersionUID = 1L;
	public static final String ENTITY_NAME = "Bank Transaction";

	@Override
	public String getMessage() {
		return String.format("The bank transaction you are trying to perform cannot and should not be a TRANSFER"
				+ "and is therefore rejected. "
				+ "Example of transactions accepted is withdrawal, debit, credit, deposit or bank fee.");
	}

}
