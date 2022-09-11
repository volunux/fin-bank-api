package com.fintest.testifi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.dto.BankAccountPinUpdateDto;
import com.fintest.testifi.domain.exception.BankAccountPinException;
import com.fintest.testifi.service.BankAccountService;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class BankAccountControllerTest {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeAll
	void setup() {
		System.out.println("Initializing Bank Account Controller Test");
	}
	
	@AfterAll
	void teardown() {
		System.out.println("Cleaning up Bank Account Controller Test");
	}
	
	@Test
	void findCustomerBankAccounts() {
		List<BankAccount> bankAccounts = bankAccountService.findAllBankAccount((long) 1);
		assertTrue(bankAccounts.size() >= 1);
	}
	
	@Test
	void changeAccountPinIncorrect() throws Exception {
		BankAccount bankAccount = bankAccountService.findBankAccount((long) 1);
		assertThat(bankAccount).isNotNull();
		
		BankAccountPinUpdateDto bankAccountPinUpdateDto = new BankAccountPinUpdateDto();
		bankAccountPinUpdateDto.setAccountPin((long) 2007);
		bankAccountPinUpdateDto.setNewAccountPin((long) 1992);
		bankAccountPinUpdateDto.setAccountNumber(bankAccount.getAccountNumber());
		
		String bankAcctPinUpdateDtoStr = objectMapper.writeValueAsString(bankAccountPinUpdateDto);
		
		mockMvc.perform(put("/api/v1/bank-account/update-pin")
				.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(bankAcctPinUpdateDtoStr))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", containsString("is invalid or incorrect")));
	}
	
	@Test
	void changeAccountPinTwo() throws Exception {
		BankAccount bankAccount = bankAccountService.findBankAccount((long) 1);
		assertThat(bankAccount).isNotNull();
		
		BankAccountPinUpdateDto bankAccountPinUpdateDto = new BankAccountPinUpdateDto();
		bankAccountPinUpdateDto.setAccountNumber(bankAccount.getAccountNumber());
		bankAccountPinUpdateDto.setAccountPin((long) 1992);
		bankAccountPinUpdateDto.setAccountPin((long) 2003);
		
		Throwable exception = assertThrows(BankAccountPinException.class, () -> {
			bankAccountService.updateBankAccountPin(bankAccountPinUpdateDto);
		});
		
		assertThat(exception.getMessage()).contains("invalid or incorrect");
	}
	
	@Test
	void createBankAccountInstance() {
		BankAccount bankAccount = new BankAccount();
		assertThat(bankAccount.getAccountNumber()).contains("FE");
	}
	
}
