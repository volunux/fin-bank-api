package com.fintest.testifi.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.BankTransaction;
import com.fintest.testifi.domain.dto.BankAccountTransactionDto;
import com.fintest.testifi.domain.dto.BankAccountTransferDto;
import com.fintest.testifi.domain.dto.GetBankAccountDto;
import com.fintest.testifi.domain.dto.GetBankAccountTransactionDto;
import com.fintest.testifi.domain.exception.BankAccountLockedException;
import com.fintest.testifi.domain.exception.BankAccountNotFoundException;
import com.fintest.testifi.domain.exception.BankAccountPinException;
import com.fintest.testifi.domain.exception.InsufficientBankAccountBalanceException;
import com.fintest.testifi.domain.exception.InvalidBankDefaultTransaction;
import com.fintest.testifi.domain.exception.InvalidBankTransferTransaction;
import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.domain.other.BankTransactionStatus;
import com.fintest.testifi.domain.other.BankTransactionType;
import com.fintest.testifi.repository.BankAccountRepository;
import com.fintest.testifi.repository.BankTransactionRepository;
import com.fintest.testifi.repository.jpa.BankAccountJpaRepository;
import com.fintest.testifi.repository.jpa.BankTransactionJpaRepository;
import com.fintest.testifi.service.BankTransactionService;
import com.fintest.testifi.util.FinBankUtil;
import com.fintest.testifi.util.TransactionTypeService;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {
	
	private BankTransactionRepository repository;
	private BankTransactionJpaRepository jpaRepository;
	private BankAccountRepository bankAccountRepository;
	private BankAccountJpaRepository bankAccountJpaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TransactionTypeService transactionTypeService; 
	
	public BankTransactionServiceImpl(BankTransactionRepository repository, BankTransactionJpaRepository jpaRepository, BankAccountRepository bankAccountRepository, BankAccountJpaRepository bankAccountJpaRepository) {
		this.repository = repository;
		this.jpaRepository = jpaRepository;
		this.bankAccountRepository = bankAccountRepository;
		this.bankAccountJpaRepository = bankAccountJpaRepository;
	}
	
	@Override
	@Transactional
	public BankTransaction createBankTransaction(BankAccountTransactionDto bankAccountTransactionDto) {
		BankAccount existingBankAccount = bankAccountJpaRepository.findByAccountNumber(bankAccountTransactionDto.getAccountNumber());
		BankTransactionType bankTransactionType = FinBankUtil.getBankTransactionType(bankAccountTransactionDto.getTransactionType());
		BankTransaction bankTransaction = new BankTransaction();
		
		if (bankTransactionType == BankTransactionType.TRANSFER) {
			throw new InvalidBankDefaultTransaction();
		}
		
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
			if (bankTransactionType != BankTransactionType.DEPOSIT && bankTransactionType != BankTransactionType.CREDIT) {
				throw new InsufficientBankAccountBalanceException(existingBankAccount.getAccountNumber());
			}
		}
		
		transactionTypeService.performTransaction(existingBankAccount, bankTransactionType, bankAccountTransactionDto.getAmount(), null);
		bankAccountRepository.update(existingBankAccount);
		bankTransaction.setTransactionStatus(BankTransactionStatus.SUCCESS);
		return repository.createBankTransaction(bankTransaction);
	}
	
	@Override
	@Transactional
	public BankTransaction createBankTransferTransaction(BankAccountTransferDto bankAccountTransferDto) {
		
		BankTransactionType transferBankTransactionType = FinBankUtil.getBankTransactionType(bankAccountTransferDto.getTransactionType());

		if (transferBankTransactionType != BankTransactionType.TRANSFER) {
			throw new InvalidBankTransferTransaction();
		}

		BankAccount initiatorAccount = bankAccountJpaRepository.findByAccountNumber(bankAccountTransferDto.getAccountNumberFrom());
		BankAccount recipientAccount = bankAccountJpaRepository.findByAccountNumber(bankAccountTransferDto.getAccountNumberTo());
		BankTransactionType bankTransactionType = FinBankUtil.getBankTransactionType(bankAccountTransferDto.getTransactionType());
		BankTransaction bankTransaction = new BankTransaction();

		if (initiatorAccount == null) {
			throw new BankAccountNotFoundException(bankAccountTransferDto.getAccountNumberFrom());
		}
		else if (recipientAccount == null) {
			throw new BankAccountNotFoundException(bankAccountTransferDto.getAccountNumberTo());
		}
		
		if (initiatorAccount.getAccountStatus() != BankAccountStatus.ACTIVE) {
			throw new BankAccountLockedException(initiatorAccount.getAccountNumber());
		}
		else if (recipientAccount.getAccountStatus() != BankAccountStatus.ACTIVE) {
			throw new BankAccountLockedException(recipientAccount.getAccountNumber());
		}
		
		boolean accountPinValid = passwordEncoder.matches(bankAccountTransferDto.getAccountPin().toString(), initiatorAccount.getAccountPin());
		
		if (!accountPinValid) {
			throw new BankAccountPinException(bankAccountTransferDto.getAccountNumberFrom(), bankAccountTransferDto.getAccountPin());		
		}
		
		bankTransaction.setInitiatorAccount(initiatorAccount);
		bankTransaction.setRecipientAccount(recipientAccount);
		bankTransaction.setAmount(bankAccountTransferDto.getAmount());
		bankTransaction.setDescription(bankAccountTransferDto.getDescription());
		bankTransaction.setTransactionType(bankTransactionType);
		
		if (initiatorAccount.getBalance() < bankAccountTransferDto.getAmount()) {
			throw new InsufficientBankAccountBalanceException(initiatorAccount.getAccountNumber());
		}
		
		transactionTypeService.performTransaction(initiatorAccount, bankTransactionType, bankAccountTransferDto.getAmount(), recipientAccount);
		bankAccountRepository.update(initiatorAccount);
		bankAccountRepository.update(recipientAccount);
		bankTransaction.setTransactionStatus(BankTransactionStatus.SUCCESS);
		return repository.createBankTransaction(bankTransaction);
	}
	
	public GetBankAccountDto findBankAccountTransactions(GetBankAccountTransactionDto getBankAccountTransactionDto) {		
		BankAccount bankAccount = bankAccountJpaRepository.findByAccountNumber(getBankAccountTransactionDto.getAccountNumber());
		Set<BankTransaction> bankTransactions = jpaRepository.findByInitiatorAccount(bankAccount);
		
		GetBankAccountDto getBankAccountDto = new GetBankAccountDto();
		getBankAccountDto.setBankAccount(bankAccount);
		getBankAccountDto.setBankTransactions(bankTransactions);
		
		return getBankAccountDto;
	}
}
