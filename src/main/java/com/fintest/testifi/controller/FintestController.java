package com.fintest.testifi.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.Fintest;

@RestController
@RequestMapping(value ="" ,
				produces = { MediaType.APPLICATION_JSON_VALUE })
public class FintestController {	

	@GetMapping
	public Fintest fintest(@RequestParam(name ="emailAddress", required = false, defaultValue = "david@gmail.com") String emailAddress) {
		return new Fintest();
	}	
}

