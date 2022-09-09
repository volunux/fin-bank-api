package com.fintest.testifi.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.BankTransaction;

@RestController
@RequestMapping(value = "bank-transaction", 
				produces = { MediaType.APPLICATION_JSON_VALUE },
				consumes = { MediaType.APPLICATION_JSON_VALUE } )
public class BankTransactionController {

	@PostMapping({"transfer"})
	public BankTransaction bankAccountTransfer(@Valid @RequestBody Object bankAccountTransferDto) {
		return null;
	}
	
	@PostMapping({"all"})
	public BankTransaction bankAccountTransaction(@Valid @RequestBody Object bankAccountTransactionDto) {
		return null;
	}

}
