package com.fintest.testifi.service.impl;

import javax.transaction.Transactional;

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
import com.fintest.testifi.domain.other.BankTransactionStatus;
import com.fintest.testifi.domain.other.BankTransactionType;
import com.fintest.testifi.repository.BankAccountRepository;
import com.fintest.testifi.repository.BankTransactionRepository;
import com.fintest.testifi.repository.jpa.BankAccountJpaRepository;
import com.fintest.testifi.service.BankTransactionService;
import com.fintest.testifi.util.FinBankUtil;
import com.fintest.testifi.util.TransactionTypeService;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {
	
	private BankTransactionRepository repository;
	private BankAccountRepository bankAccountRepository;
	private BankAccountJpaRepository bankAccountJpaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TransactionTypeService transactionTypeService; 
	
	public BankTransactionServiceImpl(BankTransactionRepository repository, BankAccountRepository bankAccountRepository, BankAccountJpaRepository bankAccountJpaRepository) {
		this.repository = repository;
		this.bankAccountRepository = bankAccountRepository;
		this.bankAccountJpaRepository = bankAccountJpaRepository;
	}

	@Override
	@Transactional
	public BankTransaction createBankTransaction(BankAccountTransactionDto bankAccountTransactionDto) {
		BankAccount existingBankAccount = bankAccountJpaRepository.findByAccountNumber(bankAccountTransactionDto.getAccountNumber());
		BankTransactionType bankTransactionType = FinBankUtil.getBankTransactionType(bankAccountTransactionDto.getTransactionType());
		BankTransaction bankTransaction = new BankTransaction();
		
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
		
		bankTransaction.setInitiatorAccount(existingBankAccount);
		bankTransaction.setRecipientAccount(null);
		bankTransaction.setAmount(bankAccountTransactionDto.getAmount());
		bankTransaction.setDescription(bankAccountTransactionDto.getDescription());
		bankTransaction.setTransactionType(bankTransactionType);
		
		if (existingBankAccount.getBalance() < bankAccountTransactionDto.getAmount()) {
			bankTransaction.setTransactionStatus(BankTransactionStatus.FAILED);
			repository.createBankTransaction(bankTransaction);
			throw new InsufficientBankAccountBalanceException(existingBankAccount.getAccountNumber());
		}
		
		transactionTypeService.performTransaction(existingBankAccount, bankTransactionType, bankAccountTransactionDto.getAmount(), null);
		bankAccountRepository.update(existingBankAccount);
		bankTransaction.setTransactionStatus(BankTransactionStatus.SUCCESS);
		return repository.createBankTransaction(bankTransaction);
	}
	
	@Override
	@Transactional
	public BankTransaction createBankTransferTransaction(BankAccountTransferDto bankAccountTransferDto) {
		BankAccount initiatorBankAccount = bankAccountJpaRepository.findByAccountNumber(bankAccountTransferDto.getAccountNumberFrom());
		BankAccount recipientBankAccount = bankAccountJpaRepository.findByAccountNumber(bankAccountTransferDto.getAccountNumberTo());
		BankTransactionType bankTransactionType = FinBankUtil.getBankTransactionType(bankAccountTransferDto.getTransactionType());
		BankTransaction bankTransaction = new BankTransaction();

		if (initiatorBankAccount == null) {
			throw new BankAccountNotFoundException(bankAccountTransferDto.getAccountNumberFrom());
		}
		else if (recipientBankAccount == null) {
			throw new BankAccountNotFoundException(bankAccountTransferDto.getAccountNumberTo());
		}
		
		if (initiatorBankAccount.getAccountStatus() != BankAccountStatus.ACTIVE) {
			throw new BankAccountLockedException(initiatorBankAccount.getAccountNumber());
		}
		else if (recipientBankAccount.getAccountStatus() != BankAccountStatus.ACTIVE) {
			throw new BankAccountLockedException(initiatorBankAccount.getAccountNumber());
		}
		
		boolean accountPinValid = passwordEncoder.matches(bankAccountTransferDto.getAccountPin().toString(), initiatorBankAccount.getAccountPin());
		
		if (!accountPinValid) {
			throw new BankAccountPinException(bankAccountTransferDto.getAccountNumberFrom(), bankAccountTransferDto.getAccountPin());		
		}
		
		bankTransaction.setInitiatorAccount(initiatorBankAccount);
		bankTransaction.setRecipientAccount(recipientBankAccount);
		bankTransaction.setAmount(bankAccountTransferDto.getAmount());
		bankTransaction.setDescription(bankAccountTransferDto.getDescription());
		bankTransaction.setTransactionType(bankTransactionType);
		
		if (initiatorBankAccount.getBalance() < bankAccountTransferDto.getAmount()) {
			bankTransaction.setTransactionStatus(BankTransactionStatus.FAILED);
			repository.createBankTransaction(bankTransaction);
			throw new InsufficientBankAccountBalanceException(initiatorBankAccount.getAccountNumber());
		}
		
		transactionTypeService.performTransaction(initiatorBankAccount, bankTransactionType, bankAccountTransferDto.getAmount(), recipientBankAccount);
		bankAccountRepository.update(initiatorBankAccount);
		bankAccountRepository.update(recipientBankAccount);
		bankTransaction.setTransactionStatus(BankTransactionStatus.SUCCESS);
		return repository.createBankTransaction(bankTransaction);
	}
}
