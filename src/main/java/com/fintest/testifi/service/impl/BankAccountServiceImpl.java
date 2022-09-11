package com.fintest.testifi.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.BankAccountDto;
import com.fintest.testifi.domain.dto.BankAccountPinUpdateDto;
import com.fintest.testifi.domain.dto.BankAccountStatusUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyBankAccountDto;
import com.fintest.testifi.domain.exception.BankAccountLockedException;
import com.fintest.testifi.domain.exception.BankAccountNotFoundException;
import com.fintest.testifi.domain.exception.BankAccountPinException;
import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.repository.BankAccountRepository;
import com.fintest.testifi.repository.jpa.BankAccountJpaRepository;
import com.fintest.testifi.service.BankAccountService;
import com.fintest.testifi.util.FinBankUtil;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	
	private BankAccountRepository repository;
	private BankAccountJpaRepository jpaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	public BankAccountServiceImpl(BankAccountRepository repository, BankAccountJpaRepository jpaRepository) {
		this.repository = repository;
		this.jpaRepository = jpaRepository;
	}
	
	@Override
	public List<BankAccount> findAllBankAccount(Long customerId) {
		Customer customer = new Customer();
		customer.setId(customerId);
		return repository.findAll(customer);
	}
	
	@Override
	public BankAccount findBankAccount(Long id) {
		BankAccount bankAccount = repository.findOne(id);
		if (bankAccount == null) {
			throw new BankAccountNotFoundException(id);
		}
		return bankAccount;
	}
	
	@Override
	public BankAccount findBankAccountByAccountNumber(String accountNumber) {
		BankAccount bankAccount = jpaRepository.findByAccountNumber(accountNumber);
		if (bankAccount == null) {
			throw new BankAccountNotFoundException(accountNumber);
		}
		return bankAccount;
	}
	
	@Override
	@Transactional
	public BankAccount createBankAccount(BankAccountDto bankAccountDto) {
		BankAccount existingBankAccount = findBankAccountByAccountNumber(bankAccountDto.getExistingAccountNumber());		
		boolean accountPinValid = passwordEncoder.matches(bankAccountDto.getExistingAccountPin().toString(), existingBankAccount.getAccountPin());
		
		if (!accountPinValid) {
			throw new BankAccountPinException(bankAccountDto.getExistingAccountNumber(), bankAccountDto.getExistingAccountPin());		
		}
		
		Customer customer = existingBankAccount.getCustomer();
		BankAccount bankAccount = new BankAccount();
		bankAccount.setCustomer(customer);
		bankAccount.setBalance(bankAccountDto.getInitialDeposit());
		bankAccount.setInterestRate(FinBankUtil.getDefaultInterestRate());
		bankAccount.setAccountStatus(BankAccountStatus.ACTIVE);
		bankAccount.setAccountType(FinBankUtil.getBankAccountType(bankAccountDto.getAccountType()));
		
		String accountPinHash = passwordEncoder.encode(String.valueOf(bankAccountDto.getNewAccountPin()));
		bankAccount.setAccountPin(accountPinHash);
		
		return repository.save(bankAccount);
	}
	
	@Override
	@Transactional
	public BankAccount updateBankAccount(Long id, Object bankAccountUpdateDto) {
		BankAccount existingBankAccount = repository.findOne(id);
		if (existingBankAccount == null) {
			throw new BankAccountNotFoundException(id);
		}
		
		BankAccount bankAccount = new BankAccount();
		bankAccount = modelMapper.map(bankAccountUpdateDto, BankAccount.class);
		bankAccount.setId(id);
		return repository.update(bankAccount);
	}
	
	@Override
	@Transactional
	public boolean updateBankAccountPin(BankAccountPinUpdateDto bankAccountPinUpdateDto) {
		BankAccount existingBankAccount = jpaRepository.findByAccountNumber(bankAccountPinUpdateDto.getAccountNumber());
		System.out.println(existingBankAccount);
		if (existingBankAccount == null) {
			throw new BankAccountNotFoundException(bankAccountPinUpdateDto.getAccountNumber());
		}

		boolean accountPinValid = passwordEncoder.matches(bankAccountPinUpdateDto.getAccountPin().toString(), existingBankAccount.getAccountPin());
		
		if (!accountPinValid) {
			throw new BankAccountPinException(bankAccountPinUpdateDto.getAccountNumber(), bankAccountPinUpdateDto.getAccountPin());		
		}
		
		if (existingBankAccount.getAccountStatus() != BankAccountStatus.ACTIVE) {
			throw new BankAccountLockedException(existingBankAccount.getAccountNumber());
		}
		
		String accountPinHash = passwordEncoder.encode(bankAccountPinUpdateDto.getNewAccountPin().toString());
		return repository.updateAccountPin(bankAccountPinUpdateDto.getAccountNumber(), accountPinHash);
	}
	
	@Override
	@Transactional
	public boolean updateBankAccountStatus(BankAccountStatusUpdateDto bankAccountStatusUpdateDto) {
		BankAccount existingBankAccount = jpaRepository.findByAccountNumber(bankAccountStatusUpdateDto.getAccountNumber());
		if (existingBankAccount == null) {
			throw new BankAccountNotFoundException(bankAccountStatusUpdateDto.getAccountNumber());
		}

		boolean accountPinValid = passwordEncoder.matches(bankAccountStatusUpdateDto.getAccountPin().toString(), existingBankAccount.getAccountPin());
		
		if (!accountPinValid) {
			throw new BankAccountPinException(bankAccountStatusUpdateDto.getAccountNumber(), bankAccountStatusUpdateDto.getAccountPin());		
		}
		
		BankAccountStatus bankAccountStatus = FinBankUtil.getBankAccountStatus(bankAccountStatusUpdateDto.getAccountStatus());
		return repository.updateAccountStatus(bankAccountStatusUpdateDto.getAccountNumber(), bankAccountStatus, bankAccountStatusUpdateDto.getAccountPin().toString());
	}
	
	@Override
	@Transactional
	public boolean deleteBankAccount(Long id) {
		boolean removed = repository.remove(id);
		if (removed) {
			return removed;
		}
		else {
			throw new BankAccountNotFoundException(id);
		}
	}
	
	@	Override
	@Transactional
	public boolean deleteManyBankAccount(DeleteManyBankAccountDto bankAccountDto) {
		return repository.removeMany(bankAccountDto.getIds());
	}
	
	
	@Override
	@Transactional
	public boolean deleteAllBankAccount() {
		return repository.removeAllBankAccount();
	}
	
}	
