package com.fintest.testifi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fintest.testifi.controller.BankAccountController;
import com.fintest.testifi.controller.BankTransactionController;
import com.fintest.testifi.controller.CustomerController;
import com.fintest.testifi.controller.FintestController;

@TestInstance(value = Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class FintestControllerTest {
	
	@Autowired
	private FintestController fintestController;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private BankAccountController bankAccountController;
	
	@Autowired
	private BankTransactionController bankTransactionController;
		
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeAll
	public void setup() {
		System.out.println("Initializing Fintest Controller Test");
	}
	
	@AfterAll
	public void tearDown() {
		System.out.println("Cleaning up Fintest Controller Test");
	}
	
	@Test
	public void contextLoads() {
		assertThat(fintestController).isNotNull();
		assertThat(customerController).isNotNull();
		assertThat(bankAccountController).isNotNull();
		assertThat(bankTransactionController).isNotNull();
	}
	
	@Test
	public void homepageShouldReturnFintest() throws Exception {
		this.mockMvc.perform(get("/api/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", 
					is("Fintest Application")));
	}

}
