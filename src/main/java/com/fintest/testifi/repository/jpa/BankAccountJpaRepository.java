package com.fintest.testifi.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintest.testifi.domain.BankAccount;

public interface BankAccountJpaRepository extends JpaRepository<BankAccount, Long> {

	BankAccount findByAccountNumber(String accountNumber);
}
