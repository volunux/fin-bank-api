package com.fintest.testifi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.BankAccountDto;
import com.fintest.testifi.domain.dto.BankAccountPinUpdateDto;
import com.fintest.testifi.domain.dto.BankAccountStatusUpdateDto;
import com.fintest.testifi.service.BankAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value ="bank-account" ,
				consumes = { MediaType.APPLICATION_JSON_VALUE } ,
					produces = { MediaType.APPLICATION_JSON_VALUE })
public class BankAccountController {

	private BankAccountService service;

	public BankAccountController(BankAccountService service) {
		this.service = service;
	}
	
	@Operation(summary = "Retrieve bank accounts", description = "Retrieve a list of bank accounts")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bank accounts retrieved successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Customer.class) 
	        						)
	        				}
	        ) })
	@GetMapping
	public List<BankAccount> findBankAccounts(@Parameter(name ="customerId", example = "1", required = false, 
														 description = "Retrieve a list of bank accounts associated with a customer") 
											  @RequestParam(required = false, name ="customerId") Long customerId) {
		return service.findAllBankAccount(customerId);
	}
	
	@Operation(summary = "Retrieve a single bank account", description = "Get a single bank account details")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bank account retrieved successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Customer.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
	        @ApiResponse(responseCode = "404", description = "Bank account cannot be found or does not exists", content = @Content)
		})
	@GetMapping({"{id}", "/detail/{id}"})
	public BankAccount findBankAccount(@PathVariable Long id) {
		return service.findBankAccount(id);
	}
		
	@Operation(summary = "Create a bank account", description = "Create a new bank account and assign to an existing customer")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bank account opened successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = BankAccount.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to perform create new bank account", content = @Content),
		})
	@PostMapping({"" , "/save"})
	public BankAccount saveBankAccount(@Valid @RequestBody BankAccountDto bankAccountDto) {
		return service.createBankAccount(bankAccountDto);
	}
	
	@PutMapping({"/update-pin"})
	@Operation(summary = "Change bank account pin", description = "Change and set new transaction pin to bank account")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bank account's transaction pin updated successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Boolean.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to perform update transaction pin", content = @Content),
		})
	public boolean updateBankAccountPin(@Valid @RequestBody BankAccountPinUpdateDto bankAccountPinUpdateDto) {
		return service.updateBankAccountPin(bankAccountPinUpdateDto);
	}
	
	@PutMapping({"/update-status"})
	@Operation(summary = "Change bank account status", description = "Change and set new status to bank account")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bank account's status updated successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Boolean.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to perform update bank account status", content = @Content),
		})
	public boolean updateBankAccountStatus(@Valid @RequestBody BankAccountStatusUpdateDto bankAccountStatusUpdateDto) {
		return service.updateBankAccountStatus(bankAccountStatusUpdateDto);
	}
		
}
