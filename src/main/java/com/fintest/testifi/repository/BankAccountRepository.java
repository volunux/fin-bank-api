package com.fintest.testifi.repository;

import java.util.List;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.other.BankAccountStatus;

public interface BankAccountRepository {

	List<BankAccount> findAll(Long customerId);
	
	BankAccount findOne(Long id);
	
	BankAccount save(BankAccount bankAccount);
	
	BankAccount update(BankAccount bankAccount);
	
	boolean updateAccountPin(String accountNumber, Long newAccountPin);
	
	boolean updateAccountStatus(String accountNumber, String bankAccountStatus, Long newAccountPin);
	
	boolean remove(Long id);
	
	boolean removeMany(List<Long> ids);
	
	boolean removeAllBankAccount();

}
