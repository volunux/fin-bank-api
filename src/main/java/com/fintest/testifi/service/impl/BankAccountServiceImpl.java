package com.fintest.testifi.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.dto.BankAccountDto;
import com.fintest.testifi.domain.dto.BankAccountUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyBankAccountDto;
import com.fintest.testifi.domain.exception.BankAccountNotFoundException;
import com.fintest.testifi.repository.BankAccountRepository;
import com.fintest.testifi.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	
	private BankAccountRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
		
	public BankAccountServiceImpl(BankAccountRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public List<BankAccount> findAllBankAccount(Long customerId) {
		return repository.findAll(customerId);
	}
	
	@Override
	public BankAccount findBankAccount(Long id) {
		BankAccount bankAccount = repository.findOne(id);
		if (bankAccount != null) {
			return bankAccount;
		}
		else {
			throw new BankAccountNotFoundException(id);
		}
	}
	
	@Override
	@Transactional
	public BankAccount createBankAccount(BankAccountDto bankAccountDto) {
		BankAccount bankAccount = new BankAccount();
		bankAccount = modelMapper.map(bankAccountDto, BankAccount.class);
		return repository.save(bankAccount);
	}
	
	@Override
	@Transactional
	public BankAccount updateBankAccount(Long id, BankAccountUpdateDto bankAccountUpdateDto) {
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
