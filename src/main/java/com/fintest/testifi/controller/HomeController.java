package com.fintest.testifi.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.Fintest;

@RestController
@RequestMapping(value ="" ,
				consumes = { MediaType.APPLICATION_JSON_VALUE } ,
				produces = { MediaType.APPLICATION_JSON_VALUE })
public class HomeController {

	@GetMapping
	public Fintest homepage() {
		return new Fintest();
	}

}
