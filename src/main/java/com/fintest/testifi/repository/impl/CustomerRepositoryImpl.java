package com.fintest.testifi.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	@Override
	public List<Customer> findAll() {
		return null;
	}
	
	@Override
	public Customer findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Customer save(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Customer update(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean remove(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeMany(List<Long> ids) {
		// TODO Auto-generated method stub
		return false;
	}
}
