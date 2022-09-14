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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "bank-transaction", 
				produces = { MediaType.APPLICATION_JSON_VALUE },
				consumes = { MediaType.APPLICATION_JSON_VALUE } )
public class BankTransactionController {
	
	private BankTransactionService bankTransactionService;
	
	public BankTransactionController(BankTransactionService bankTransactionService) {
		this.bankTransactionService = bankTransactionService;
	}

	@Operation(summary = "Money transfer", description = "Transfer money from one bank account to another")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Money transfer transaction successful", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = BankTransaction.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to perform money transfer", content = @Content),
		})
	@PostMapping({"transfer"})
	public BankTransaction bankAccountTransfer(@Valid @RequestBody BankAccountTransferDto bankAccountTransferDto) {
		return bankTransactionService.createBankTransferTransaction(bankAccountTransferDto);
	}
	
	@Operation(summary = "Bank account transaction", description = "Transaction to perform on bank account whether withdrawal, deposit, credit or debit")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bank account transaction successful", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = BankTransaction.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to perform bank transacton", content = @Content),
		})
	@PostMapping({"all"})
	public BankTransaction bankAccountTransaction(@Valid @RequestBody BankAccountTransactionDto bankAccountTransactionDto) {
		return bankTransactionService.createBankTransaction(bankAccountTransactionDto);
	}
	
	@Operation(summary = "Retrieve bank account details", description = "Retrieve single bank account and its associated bank transactions")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bank account details retrieved successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = GetBankAccountDto.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters retrieve bank account details", content = @Content),
		})
	@PostMapping({"/detail"})
	public GetBankAccountDto findBankAccountTransactions(@Valid @RequestBody GetBankAccountTransactionDto getBankAccountTransactionDto) {
		return bankTransactionService.findBankAccountTransactions(getBankAccountTransactionDto);
	}

}
