package com.fintest.testifi.repository;

import java.util.List;

import com.fintest.testifi.domain.Customer;

public interface CustomerRepository {
	
	List<Customer> findAll(String emailAddress, Integer pageOffset);
	
	Customer findOne(Long id);
	
	Customer save(Customer customer);
	
	Customer update(Customer customer);
	
	boolean existCustomerEmail(String emailAddress);
	
	boolean remove(Long id);
	
	boolean removeMany(List<Long> ids);
	
	boolean removeAllCustomer();
}
