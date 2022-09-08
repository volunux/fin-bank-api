package com.fintest.testifi.repository.impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.exception.BankAccountDuplicateEntityException;
import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.repository.BankAccountRepository;
import com.fintest.testifi.util.FinBankUtil;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<BankAccount> findAll(Long customerId) {
    	String queryText = "select ba from BankAccount ba";
    	TypedQuery<BankAccount> bankAccountQuery = entityManager.createQuery(queryText, BankAccount.class);

    	if (customerId != null ) {
    		queryText = "select ba from BankAccount ba where ba.customer = :customer";
    		bankAccountQuery.setParameter("customer", customerId);
    	}
    	
    	return bankAccountQuery.getResultList();
	}
	
	@Override
	public BankAccount findOne(Long id) {
        return entityManager.find(BankAccount.class, id);
	}
	
	@Override
	public BankAccount save(BankAccount bankAccount) {
        try {
        	entityManager.persist(bankAccount);
        	return bankAccount;
        }
        catch (EntityExistsException ex) {
        	throw new BankAccountDuplicateEntityException();
        }
	}
	
	@Override
	public BankAccount update(BankAccount bankAccount) {
		return entityManager.merge(bankAccount);
	}
	
	@Override
	public boolean updateAccountPin(String accountNumber, Long accountPin) {
		
		String queryText = "UPDATE BankAccount ba SET ba.accountPin = :newAccountPin where ba.accountNumber = :accountNumber";
		Query query = entityManager.createQuery(queryText);
		
		query.setParameter("accountNumber", accountNumber);
		query.setParameter("newAccountPin", accountPin);
		
		int result = query.executeUpdate();
		return result > 1;
	}
	
	@Override
	public boolean updateAccountStatus(String accountNumber, String bankAccountStatus, Long accountPin) {
		
		String queryText = "UPDATE BankAccount ba SET ba.accountStatus = :newAccountStatus where ba.accountNumber = :accountNumber";
		Query query = entityManager.createQuery(queryText);
		
		query.setParameter("accountNumber", accountNumber);
		query.setParameter("newAccountStatus", bankAccountStatus);
		
		int result = query.executeUpdate();
		return result > 1;
	}
	
	@Override
	public boolean remove(Long id) {
		try {
			BankAccount bankAccount = entityManager.getReference(BankAccount.class, id);
			entityManager.remove(bankAccount);
			return true;
		}
		catch (EntityNotFoundException ex) {
			return false;
		}
	}
	
	@Override
	public boolean removeMany(List<Long> bankAccountIds) {
		Query query = entityManager.createQuery("delete from BankAccount ba where ba.id in (:ids)");
		query.setParameter("ids", bankAccountIds);
		if (query.executeUpdate() > 0) {
			return true;
		}				
		else {
			return false;
		}
	}
	
	@Override
	public boolean removeAllBankAccount() {
		Query query = entityManager.createQuery("delete from BankAccount");
		if (query.executeUpdate() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

}
