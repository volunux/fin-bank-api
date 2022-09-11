package com.fintest.testifi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class Service4ApplicationTests {
	
	@Autowired
	private ApplicationContext applicationContext;

	@BeforeAll
	void setup() {
		System.out.println("Initializing Fintest & FinBank application");
	}
	
	@Test
	void contextCheck() {
		assertThat(applicationContext).isNotNull();
	}
	

}
