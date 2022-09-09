package com.fintest.testifi.service.impl;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.BankTransaction;
import com.fintest.testifi.domain.dto.BankAccountTransactionDto;
import com.fintest.testifi.domain.dto.BankAccountTransferDto;
import com.fintest.testifi.domain.exception.BankAccountLockedException;
import com.fintest.testifi.domain.exception.BankAccountNotFoundException;
import com.fintest.testifi.domain.exception.BankAccountPinException;
import com.fintest.testifi.domain.exception.InsufficientBankAccountBalanceException;
import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.domain.other.BankTransactionType;
import com.fintest.testifi.repository.BankTransactionRepository;
import com.fintest.testifi.repository.jpa.BankAccountJpaRepository;
import com.fintest.testifi.service.BankTransactionService;
import com.fintest.testifi.util.FinBankUtil;
import com.fintest.testifi.util.TransactionTypeService;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {
	
	private BankTransactionRepository repository;
	private BankAccountJpaRepository bankAccountJpaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TransactionTypeService transactionTypeService; 
	
	public BankTransactionServiceImpl(BankTransactionRepository repository, BankAccountJpaRepository bankAccountJpaRepository) {
		this.repository = repository;
		this.bankAccountJpaRepository = bankAccountJpaRepository;
	}

	@Override
	@Transactional
	public BankTransaction createBankTransaction(BankAccountTransactionDto bankAccountTransactionDto) {
		BankAccount existingBankAccount = bankAccountJpaRepository.findByAccountNumber(bankAccountTransactionDto.getAccountNumber());
		
		if (existingBankAccount == null) {
			throw new BankAccountNotFoundException(bankAccountTransactionDto.getAccountNumber());
		}
		
		if (existingBankAccount.getAccountStatus() != BankAccountStatus.ACTIVE) {
			throw new BankAccountLockedException(existingBankAccount.getAccountNumber());
		}
		
		boolean accountPinValid = passwordEncoder.matches(bankAccountTransactionDto.getAccountPin().toString(), existingBankAccount.getAccountPin());
		
		if (!accountPinValid) {
			throw new BankAccountPinException(bankAccountTransactionDto.getAccountNumber(), bankAccountTransactionDto.getAccountPin());		
		}
		
		if (existingBankAccount.getBalance() < bankAccountTransactionDto.getAmount()) {
			throw new InsufficientBankAccountBalanceException(existingBankAccount.getAccountNumber());
		}
		
		BankTransaction bankTransaction = new BankTransaction();
		bankTransaction = modelMapper.map(bankAccountTransactionDto, BankTransaction.class);
		
		BankTransactionType bankTransactionType = FinBankUtil.getBankTransactionType(bankAccountTransactionDto.getTransactionType());
		transactionTypeService.performTransaction(existingBankAccount, bankTransactionType, bankAccountTransactionDto.getAmount());
		
		return repository.createBankTransaction(bankTransaction);
	}
	
	@Override
	@Transactional
	public BankTransaction createBankTransferTransaction(BankAccountTransferDto bankAccountTransferDto) {
		BankAccount existingBankAccountFrom = bankAccountJpaRepository.findByAccountNumber(bankAccountTransferDto.getAccountNumberFrom());
		BankAccount existingBankAccountTo = bankAccountJpaRepository.findByAccountNumber(bankAccountTransferDto.getAccountNumberTo());

		if (existingBankAccountFrom == null) {
			throw new BankAccountNotFoundException(bankAccountTransferDto.getAccountNumberFrom());
		}
		else if (existingBankAccountTo == null) {
			throw new BankAccountNotFoundException(bankAccountTransferDto.getAccountNumberTo());
		}
		
		BankTransaction bankTransaction = new BankTransaction();
		bankTransaction = modelMapper.map(bankAccountTransferDto, BankTransaction.class);
		return repository.createBankTransaction(bankTransaction);
	}
}
