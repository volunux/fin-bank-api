package com.fintest.testifi.repository.jpa;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.BankTransaction;

public interface BankTransactionJpaRepository extends JpaRepository<BankTransaction, Long> {
	
	Set<BankTransaction> findByInitiatorAccount(BankAccount initiatorAccount);
}
