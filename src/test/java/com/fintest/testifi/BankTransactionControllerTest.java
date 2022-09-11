package com.fintest.testifi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.BankTransaction;
import com.fintest.testifi.domain.dto.BankAccountTransactionDto;
import com.fintest.testifi.domain.dto.BankAccountTransferDto;
import com.fintest.testifi.domain.exception.BankAccountPinException;
import com.fintest.testifi.domain.exception.InsufficientBankAccountBalanceException;
import com.fintest.testifi.domain.exception.InvalidBankDefaultTransaction;
import com.fintest.testifi.domain.exception.InvalidBankTransferTransaction;
import com.fintest.testifi.domain.other.BankTransactionStatus;
import com.fintest.testifi.domain.other.BankTransactionType;
import com.fintest.testifi.service.BankAccountService;
import com.fintest.testifi.service.BankTransactionService;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class BankTransactionControllerTest {
	
	@Autowired
	private BankTransactionService bankTransactionService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	BankAccount initiatorAccount;
	BankAccount recipientAccount;
	BankAccountTransferDto bankAccountTransferDto;
	BankAccountTransactionDto bankAccountTransactionDto;
		
	@BeforeEach
	void setup() {
		System.out.println("Initializing Bank Transaction Controller Test");

		initiatorAccount = bankAccountService.findBankAccount((long) 3);
		recipientAccount = bankAccountService.findBankAccount((long) 2);
		bankAccountTransferDto = new BankAccountTransferDto();
		bankAccountTransferDto.setAccountNumberFrom(initiatorAccount.getAccountNumber());
		bankAccountTransferDto.setAccountNumberTo(recipientAccount.getAccountNumber());
		bankAccountTransferDto.setAccountPin((long) 1997);
		bankAccountTransferDto.setDescription("Transfer of money");
		bankAccountTransferDto.setTransactionType("transfer");
		
		bankAccountTransactionDto = new BankAccountTransactionDto();
		bankAccountTransactionDto.setAccountNumber(recipientAccount.getAccountNumber());
		bankAccountTransactionDto.setAccountPin((long) 1997);
		bankAccountTransactionDto.setAmount((double) 1);
		bankAccountTransactionDto.setDescription("Withdrawal of money");
		bankAccountTransactionDto.setTransactionType("withdrawal");
	}
	
	@AfterAll
	void teardown() {
		System.out.println("Cleaning up Bank Transaction Controller Test");
	}
	
	@Test
	void attemptTransferFailure() {
		bankAccountTransferDto.setTransactionType("withdrawal");
			Throwable exception = assertThrows(InvalidBankTransferTransaction.class, () -> {
			bankTransactionService.createBankTransferTransaction(bankAccountTransferDto);
		});
		
		assertThat(exception.getMessage()).contains("you are trying to perform is not a TRANSFER");
	}
	
	@Test
	void attemptTransferFailureDueToPin() {
		bankAccountTransferDto.setAccountPin((long) 1995);
		Throwable exception = assertThrows(BankAccountPinException.class, () -> {
			bankTransactionService.createBankTransferTransaction(bankAccountTransferDto);
		});
		
		assertThat(exception.getMessage()).contains("is invalid or incorrect");
	}
	
	@Test
	void attemptTransferFailureDueToInsufficientBalance() {
		bankAccountTransferDto.setAmount((double) 500);
		Throwable exception = assertThrows(InsufficientBankAccountBalanceException.class, () -> {
			bankTransactionService.createBankTransferTransaction(bankAccountTransferDto);
		});
		
		assertThat(exception.getMessage()).contains("has an insufficient balance");
	}
	
	@Test
	void attemptTransferSuccess() {
		bankAccountTransferDto.setAmount((double) 3);
		BankTransaction bankTransaction = bankTransactionService.createBankTransferTransaction(bankAccountTransferDto);
		assertEquals(bankTransaction.getTransactionStatus(), BankTransactionStatus.SUCCESS, "Bank Transaction success");
	}
	
	@Test
	void attemptWithdrawlSuccess() {
		bankAccountTransactionDto.setTransactionType("withdrawal");
		BankTransaction bankTransaction = bankTransactionService.createBankTransaction(bankAccountTransactionDto);
		assertEquals(bankTransaction.getTransactionType(), BankTransactionType.WITHDRAWAL, "Bank Transaction and withdrawal a success");
		assertEquals(bankTransaction.getTransactionStatus(), BankTransactionStatus.SUCCESS, "Bank Transaction success");
	}
	
	@Test
	void attemptDebitSuccess() {
		bankAccountTransactionDto.setTransactionType("debit");
		bankAccountTransactionDto.setAmount((double) 2);
		BankTransaction bankTransaction = bankTransactionService.createBankTransaction(bankAccountTransactionDto);
		assertEquals(bankTransaction.getTransactionType(), BankTransactionType.DEBIT, "Bank Transaction and debit a success");
		assertEquals(bankTransaction.getTransactionStatus(), BankTransactionStatus.SUCCESS, "Bank Transaction success");
	}
	
	@Test
	void attemptInvalidTransaction() {
		bankAccountTransactionDto.setTransactionType("transfer");
		Throwable exception = assertThrows(InvalidBankDefaultTransaction.class, () -> {
			bankTransactionService.createBankTransaction(bankAccountTransactionDto);
		});
		assertThat(exception.getMessage()).contains("Example of transactions accepted is withdrawal, debit, credit, deposit ");
	}
	
	@Test
	void attemptDepositTransaction() {
		bankAccountTransactionDto.setTransactionType("deposit");
		bankAccountTransactionDto.setAmount((double) 500);
		BankTransaction bankTransaction = bankTransactionService.createBankTransaction(bankAccountTransactionDto);
		assertEquals(bankTransaction.getTransactionType(), BankTransactionType.DEPOSIT, "Bank Transaction and deposit a success");
		assertEquals(bankTransaction.getTransactionStatus(), BankTransactionStatus.SUCCESS, "Bank Transaction success");
		
		BankAccount bankAccount = bankAccountService.findBankAccountByAccountNumber(bankAccountTransactionDto.getAccountNumber());
		assertThat(bankAccount.getBalance()).isGreaterThan((double) 100);
	}

}
