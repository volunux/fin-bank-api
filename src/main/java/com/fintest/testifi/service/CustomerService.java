package com.fintest.testifi.service;

import java.util.List;

import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.CustomerDto;
import com.fintest.testifi.domain.dto.CustomerUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyCustomerDto;

public interface CustomerService {
	
	List<Customer> findAllCustomer(String emailAddress);
	
	Customer findCustomer(Long id);
	
	Customer createCustomer(CustomerDto customerDto);
	
	Customer updateCustomer(Long id, CustomerUpdateDto customerUpdateDto);
	
	boolean deleteCustomer(Long id);
	
	boolean deleteManyCustomer(DeleteManyCustomerDto customerDto);
	
	boolean existCustomerEmail(String emailAddress);

	boolean deleteAllCustomer();
}
