package com.fintest.testifi.repository;

import java.util.List;

import com.fintest.testifi.domain.Customer;

public interface CustomerRepository {
	
	List<Customer> findAll();
	
	Customer findOne(Long id);
	
	Customer save(Customer customer);
	
	Customer update(Customer customer);
	
	boolean remove(Long id);
	
	boolean removeMany(List<Long> ids);
}
