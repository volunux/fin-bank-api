package com.fintest.testifi.service;

import com.fintest.testifi.domain.BankTransaction;
import com.fintest.testifi.domain.dto.BankAccountTransactionDto;
import com.fintest.testifi.domain.dto.BankAccountTransferDto;

public interface BankTransactionService {
	
	BankTransaction createBankTransferTransaction(BankAccountTransferDto bankAccountTransferDto);
	
	BankTransaction createBankTransaction(BankAccountTransactionDto bankAccountTransactionDto);

}
