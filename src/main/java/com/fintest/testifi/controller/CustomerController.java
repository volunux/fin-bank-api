package com.fintest.testifi.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.CustomerDto;
import com.fintest.testifi.domain.dto.CustomerUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyCustomerDto;
import com.fintest.testifi.service.CustomerService;

@RestController
@RequestMapping(value ="customer" ,
				consumes = { MediaType.APPLICATION_JSON_VALUE } ,
				produces = { MediaType.APPLICATION_JSON_VALUE })
public class CustomerController {

	private CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Customer> findCustomers(@RequestParam(required = false) String emailAddress) {
		return service.findAllCustomer(emailAddress);
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
		return service.updateCustomer(id, customerUpdateDto);
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
