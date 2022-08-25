package com.fintest.testifi.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.CustomerDto;
import com.fintest.testifi.domain.dto.CustomerUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyCustomerDto;
import com.fintest.testifi.service.CustomerService;

@RestController("customer")
public class CustomerController {

	private CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Customer> findCustomers() {
		return service.findAllCustomer();
	}
	
	@GetMapping("/detail/{id}")
	public Customer findCustomer(@PathVariable Long id) {
		return service.findCustomer(id);
	}
	
	@PostMapping({"" , "/save"})
	public Customer saveCustomer(@Validated @RequestBody CustomerDto customerDto) {
		return service.createCustomer(customerDto);
	}
	
	@PutMapping({"{id}" , "/update/{id}"})
	public Customer updateCustomer(@PathVariable Long id, @Validated @RequestBody CustomerUpdateDto customerUpdateDto) {
		return service.updateCustomer(customerUpdateDto);
	}
	
	@DeleteMapping({"{id}" , "/delete/{id}"})
	public boolean deleteCustomer(@PathVariable Long id) {
		return service.deleteCustomer(id);
	}
	
	@PutMapping({"/remove/many"})
	public boolean deleteManyCustomer(@Validated @RequestBody DeleteManyCustomerDto deleteManyCustomerDto) {
		return service.deleteManyCustomer(deleteManyCustomerDto);
	}
	
}
