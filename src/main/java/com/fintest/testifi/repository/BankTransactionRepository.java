package com.fintest.testifi.repository;

import com.fintest.testifi.domain.BankTransaction;

public interface BankTransactionRepository {
	
	BankTransaction createBankTransaction(BankTransaction bankTransaction);
}
