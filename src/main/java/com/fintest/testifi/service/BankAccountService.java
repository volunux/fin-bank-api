package com.fintest.testifi.service;

import java.util.List;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.dto.BankAccountDto;
import com.fintest.testifi.domain.dto.BankAccountPinUpdateDto;
import com.fintest.testifi.domain.dto.BankAccountStatusUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyBankAccountDto;

public interface BankAccountService {
	
	List<BankAccount> findAllBankAccount(Long customerId);
	
	BankAccount findBankAccount(Long id);
	
	BankAccount createBankAccount(BankAccountDto bankDto);
	
	BankAccount updateBankAccount(Long id, Object bankAccountUpdateDto);
	
	boolean updateBankAccountPin(BankAccountPinUpdateDto bankAccountPinUpdateDto);
	
	boolean updateBankAccountStatus(BankAccountStatusUpdateDto bankAccountStatusUpdateDto);
	
	boolean deleteBankAccount(Long id);
	
	boolean deleteManyBankAccount(DeleteManyBankAccountDto customerDto);
	
	boolean deleteAllBankAccount();
}
