package com.fintest.testifi.repository.impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.exception.CustomerDuplicateEntityException;
import com.fintest.testifi.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> findAll(String emailAddress) {
    	String queryText = "select c from Customer c";
    	TypedQuery<Customer> customerQuery = entityManager.createQuery(queryText, Customer.class);

    	if (emailAddress != null ) {
    		queryText = "select c from Customer c where c.emailAddress = :emailAddress";
    		customerQuery.setParameter("emailAddress", emailAddress);
    	}
    	
    	return customerQuery.getResultList();
	}
	
	@Override
	public Customer findOne(Long id) {
        return entityManager.find(Customer.class, id);
	}
	
	@Override
	public Customer save(Customer customer) {
        try {
        	entityManager.persist(customer);
        	return customer;
        }
        catch (EntityExistsException ex) {
        	throw new CustomerDuplicateEntityException();
        }
	}
	
	@Override
	public Customer update(Customer customer) {
		return entityManager.merge(customer);
	}
	
	@Override
	public boolean remove(Long id) {
		try {
			Customer customer = entityManager.getReference(Customer.class, id);
			entityManager.remove(customer);
			return true;
		}
		catch (EntityNotFoundException ex) {
			return false;
		}
	}
	
	@Override
	public boolean removeMany(List<Long> customerIds) {
		Query query = entityManager.createQuery("delete from Customer c where c.id in (:ids)");
		query.setParameter("ids", customerIds);
		if (query.executeUpdate() > 0) {
			return true;
		}				
		else {
			return false;
		}
	}
	
	@Override
	public boolean removeAllCustomer() {
		Query query = entityManager.createQuery("delete from Customer");
		if (query.executeUpdate() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean existCustomerEmail(String emailAddress) {
		Query query = entityManager.createQuery("select c.id from Customer c where c.emailAddress = :emailAddress");
		query.setMaxResults(1);
		query.setParameter("emailAddress", emailAddress);
		if (query.getResultList().size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
}
