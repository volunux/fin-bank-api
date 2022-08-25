package com.fintest.testifi.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.CustomerDto;
import com.fintest.testifi.domain.dto.CustomerUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyCustomerDto;
import com.fintest.testifi.repository.CustomerRepository;
import com.fintest.testifi.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository repository;
		
	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public List<Customer> findAllCustomer() {
		return repository.findAll();
	}
	
	@Override
	public Customer findCustomer(Long id) {
		return repository.findOne(id);
	}
	
	@Override
	@Transactional
	public Customer createCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		return repository.save(customer);
	}
	
	@Override
	@Transactional
	public Customer updateCustomer(CustomerUpdateDto customerUpdateDto) {
		Customer customer = new Customer();
		return repository.update(customer);
	}
	
	@Override
	@Transactional
	public boolean deleteCustomer(Long id) {
		return repository.remove(id);
	}
	
	@Override
	@Transactional
	public boolean deleteManyCustomer(DeleteManyCustomerDto customerDto) {
		return repository.removeMany(customerDto.getIds());
	}
	
	
}
