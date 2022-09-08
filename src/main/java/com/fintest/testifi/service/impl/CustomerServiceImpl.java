package com.fintest.testifi.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.CustomerDto;
import com.fintest.testifi.domain.dto.CustomerUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyCustomerDto;
import com.fintest.testifi.domain.exception.CustomerNotFoundException;
import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.repository.CustomerRepository;
import com.fintest.testifi.service.BankAccountService;
import com.fintest.testifi.service.CustomerService;
import com.fintest.testifi.util.FinBankUtil;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository repository;
	private BankAccountService bankAccountService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
		
	public CustomerServiceImpl(CustomerRepository repository, BankAccountService bankAccountService) {
		this.repository = repository;
		this.bankAccountService = bankAccountService;
	}
	
	@Override
	public List<Customer> findAllCustomer(String emailAddress) {
		return repository.findAll(emailAddress);
	}
	
	@Override
	public Customer findCustomer(Long id) {
		Customer customer = repository.findOne(id);
		if (customer != null) {
			return customer;
		}
		else {
			throw new CustomerNotFoundException(id);
		}
	}
	
	@Override
	@Transactional
	public Customer createCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer = modelMapper.map(customerDto, Customer.class);
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(customerDto.getInitialDeposit());
		bankAccount.setInterestRate(FinBankUtil.getDefaultInterestRate());
		bankAccount.setCustomer(customer);
		bankAccount.setAccountType(FinBankUtil.getBankAccountType(customerDto.getAccountType()));
		bankAccount.setAccountStatus(BankAccountStatus.ACTIVE);
		
		String accountPinHash = passwordEncoder.encode(String.valueOf(customerDto.getAccountPin()));
		bankAccount.setAccountPin(accountPinHash);;
		
		customer.getBankAccounts().add(bankAccount);
		customer = repository.save(customer);
		return customer;
	}
	
	@Override
	@Transactional
	public Customer updateCustomer(Long id, CustomerUpdateDto customerUpdateDto) {
		Customer existingCustomer = repository.findOne(id);
		if (existingCustomer == null) {
			throw new CustomerNotFoundException(id);
		}

		Customer customer = new Customer();
		customer = modelMapper.map(customerUpdateDto, Customer.class);
		customer.setId(id);
		return repository.update(customer);
	}
	
	@Override
	@Transactional
	public boolean deleteCustomer(Long id) {
		boolean removed = repository.remove(id);
		if (removed) {
			return removed;
		}
		else {
			throw new CustomerNotFoundException(id);
		}
	}
	
	@	Override
	@Transactional
	public boolean deleteManyCustomer(DeleteManyCustomerDto customerDto) {
		return repository.removeMany(customerDto.getIds());
	}
	
	@Override
	@Transactional
	public boolean existCustomerEmail(String emailAddress) {
		return repository.existCustomerEmail(emailAddress);			
	}
	
	@Override
	@Transactional
	public boolean deleteAllCustomer() {
		return repository.removeAllCustomer();
	}
	
}	
