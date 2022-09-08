package com.fintest.testifi.service;

import java.util.List;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.dto.BankAccountDto;
import com.fintest.testifi.domain.dto.BankAccountUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyBankAccountDto;

public interface BankAccountService {
	
	List<BankAccount> findAllBankAccount(Long customerId);
	
	BankAccount findBankAccount(Long id);
	
	BankAccount createBankAccount(BankAccountDto bankDto);
	
	BankAccount updateBankAccount(Long id, BankAccountUpdateDto bankAccountUpdateDto);
	
	boolean deleteBankAccount(Long id);
	
	boolean deleteManyBankAccount(DeleteManyBankAccountDto customerDto);
	
	boolean deleteAllBankAccount();
}
