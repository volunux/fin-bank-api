package com.fintest.testifi.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.BankTransaction;
import com.fintest.testifi.domain.dto.BankAccountTransactionDto;
import com.fintest.testifi.domain.dto.BankAccountTransferDto;
import com.fintest.testifi.domain.dto.GetBankAccountDto;
import com.fintest.testifi.domain.dto.GetBankAccountTransactionDto;
import com.fintest.testifi.service.BankTransactionService;

@RestController
@RequestMapping(value = "bank-transaction", 
				produces = { MediaType.APPLICATION_JSON_VALUE },
				consumes = { MediaType.APPLICATION_JSON_VALUE } )
public class BankTransactionController {
	
	private BankTransactionService bankTransactionService;
	
	public BankTransactionController(BankTransactionService bankTransactionService) {
		this.bankTransactionService = bankTransactionService;
	}

	@PostMapping({"transfer"})
	public BankTransaction bankAccountTransfer(@Valid @RequestBody BankAccountTransferDto bankAccountTransferDto) {
		return bankTransactionService.createBankTransferTransaction(bankAccountTransferDto);
	}
	
	@PostMapping({"all"})
	public BankTransaction bankAccountTransaction(@Valid @RequestBody BankAccountTransactionDto bankAccountTransactionDto) {
		return bankTransactionService.createBankTransaction(bankAccountTransactionDto);
	}
	
	@PostMapping({"/detail"})
	public GetBankAccountDto findBankAccountTransactions(@Valid @RequestBody GetBankAccountTransactionDto getBankAccountTransactionDto) {
		return bankTransactionService.findBankAccountTransactions(getBankAccountTransactionDto);
	}

}
