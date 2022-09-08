package com.fintest.testifi.repository;

import java.util.List;

import com.fintest.testifi.domain.BankAccount;

public interface BankAccountRepository {

	List<BankAccount> findAll(Long customerId);
	
	BankAccount findOne(Long id);
	
	BankAccount save(BankAccount bankAccount);
	
	BankAccount update(BankAccount bankAccount);
	
	boolean remove(Long id);
	
	boolean removeMany(List<Long> ids);
	
	boolean removeAllBankAccount();

}
