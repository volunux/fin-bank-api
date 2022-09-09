package com.fintest.testifi.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fintest.testifi.domain.BankTransaction;
import com.fintest.testifi.repository.BankTransactionRepository;

@Repository
public class BankTransactionRepositoryImpl implements BankTransactionRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public BankTransaction createBankTransaction(BankTransaction bankTransaction) {
		entityManager.persist(bankTransaction);
		return bankTransaction;
	}
}
