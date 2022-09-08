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
import com.fintest.testifi.domain.dto.BankAccountDto;
import com.fintest.testifi.domain.dto.BankAccountPinUpdateDto;
import com.fintest.testifi.domain.dto.BankAccountStatusUpdateDto;
import com.fintest.testifi.domain.dto.BankAccountUpdateDto;
import com.fintest.testifi.service.BankAccountService;

@RestController
@RequestMapping(value ="bank-account" ,
				consumes = { MediaType.APPLICATION_JSON_VALUE } ,
					produces = { MediaType.APPLICATION_JSON_VALUE })
public class BankAccountController {

	private BankAccountService service;

	public BankAccountController(BankAccountService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<BankAccount> findBankAccounts(@RequestParam(required = false, name ="customerId") Long customerId) {
		return service.findAllBankAccount(customerId);
	}	
	
	@GetMapping("/detail/{id}")
	public BankAccount findBankAccount(@PathVariable Long id) {
		return service.findBankAccount(id);
	}
	
	@PostMapping({"" , "/save"})
	public BankAccount saveBankAccount(@Valid @RequestBody BankAccountDto bankAccountDto) {
		return service.createBankAccount(bankAccountDto);
	}
		
	@PutMapping({"{id}" , "/update/{id}"})
	public BankAccount updateBankAccount(@PathVariable Long id, @Valid @RequestBody BankAccountUpdateDto bankAccountUpdateDto) {
		return service.updateBankAccount(id, bankAccountUpdateDto);
	}
	
	@PutMapping({"/update-pin"})
	public boolean updateBankAccountPin(@Valid @RequestBody BankAccountPinUpdateDto bankAccountPinUpdateDto) {
		return service.updateBankAccountPin(bankAccountPinUpdateDto);
	}
	
	@PutMapping({"/update-status/"})
	public boolean updateBankAccountStatus(@Valid @RequestBody BankAccountStatusUpdateDto bankAccountStatusUpdateDto) {
		return service.updateBankAccountStatus(bankAccountStatusUpdateDto);
	}
		
}
