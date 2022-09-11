package com.fintest.testifi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;

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
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.CustomerDto;
import com.fintest.testifi.domain.dto.CustomerUpdateDto;
import com.fintest.testifi.service.CustomerService;
import com.jayway.jsonpath.JsonPath;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CustomerService customerService;
		
	@BeforeAll
	void setup() {
		System.out.println("Initializing Customer Controller Test");
	}
	
	@AfterAll
	void tearDown() {
		System.out.println("Cleaning up testing Customer Controller Test");
	}
	
	@Test
	void contextLoads() {
		assertThat(mockMvc).isNotNull();
	}
	
	@Test
	void testCustomerCreationFailure() throws Exception {
		
		CustomerDto customerDto = new CustomerDto();
		customerDto.setFullName("Musa Abbase");
		customerDto.setEmailAddress("david");
		customerDto.setPhoneNumber("09022035587");
		
		String customerDtoStr = objectMapper.writeValueAsString(customerDto);
		
		mockMvc.perform(post("/api/v1/customer/save")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(customerDtoStr))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errorType", is("dataValidation")))
		.andExpect(jsonPath("$.message.emailAddress", containsString("david@example.com")))
		.andExpect(jsonPath("$.message.phoneNumber", containsString("is not valid ")));
	}
	
	@Test
	void testCustomerCreationSuccess() throws Exception {

		CustomerDto customerDto = new CustomerDto();
		customerDto.setFullName("Musa Abbase");
		customerDto.setAccountType("saving");
		customerDto.setAccountPin((long) 1997);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DAY_OF_MONTH, 7);
		calendar.set(Calendar.YEAR, 1997);
		Date dateOfBirth = calendar.getTime();
		customerDto.setDateOfBirth(dateOfBirth);
		
		customerDto.setEmailAddress("david2@yahoo.com");
		customerDto.setHomeAddress("London, United Kingdom");
		customerDto.setInitialDeposit((double) 100);
		customerDto.setPhoneNumber("+2349022038799");
		
		String customerDtoStr = objectMapper.writeValueAsString(customerDto);
		
		MvcResult result = mockMvc.perform(post("/api/v1/customer/save")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(customerDtoStr))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.fullName", containsString("Musa")))
		.andExpect(jsonPath("$.bankAccounts", notNullValue()))
		.andReturn();
		
		Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
		Customer customer = customerService.findCustomer(Long.valueOf(id));
		
		assertThat(customer).isNotNull();
	}
	
	@Test
	void findCustomer() {
		
		Customer customer = customerService.findCustomer((long) 1);	
		assertThat(customer).isNotNull();
	}
	
	@Test
	void updateCustomer() {
		
		CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
		customerUpdateDto.setFullName("Yusuf Musa Fari");
		customerUpdateDto.setPhoneNumber("+2347040643797");
		customerUpdateDto.setEmailAddress("johndavies@gmail.com");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DAY_OF_MONTH, 7);
		calendar.set(Calendar.YEAR, 1997);
		Date dateOfBirth = calendar.getTime();
		customerUpdateDto.setDateOfBirth(dateOfBirth);
		
		customerUpdateDto.setHomeAddress("Street 2, Manchester, United Kingdom");
		
		Customer customer = customerService.updateCustomer((long) 1, customerUpdateDto);
		assertThat(customer).isNotNull();
		assertEquals(customer.getFullName(), "Yusuf Musa Fari", "Full name equals");
	}

}
