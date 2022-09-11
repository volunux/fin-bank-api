package com.fintest.testifi.repository;

import java.util.List;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.other.BankAccountStatus;

public interface BankAccountRepository {

	List<BankAccount> findAll(Customer customer);
	
	BankAccount findOne(Long id);
	
	BankAccount save(BankAccount bankAccount);
	
	BankAccount update(BankAccount bankAccount);
	
	boolean updateAccountPin(String accountNumber, String newAccountPin);
	
	boolean updateAccountStatus(String accountNumber, BankAccountStatus accountStatus, String newAccountPin);
	
	boolean remove(Long id);
	
	boolean removeMany(List<Long> ids);
	
	boolean removeAllBankAccount();

}
