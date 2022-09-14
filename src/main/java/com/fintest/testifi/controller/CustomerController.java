package com.fintest.testifi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintest.testifi.domain.Customer;
import com.fintest.testifi.domain.dto.CustomerDto;
import com.fintest.testifi.domain.dto.CustomerUpdateDto;
import com.fintest.testifi.domain.dto.DeleteManyCustomerDto;
import com.fintest.testifi.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value ="customer" ,
					produces = { MediaType.APPLICATION_JSON_VALUE })
@Tag(name = "Customer API", description = "Retrieve, modify, delete and create new customers of the bank")
@SecurityRequirement(name ="api")
public class CustomerController {

	private CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}	
	
	@Operation(summary = "Retrieve customers", description = "Retrieve a list of customers with their bank accounts")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Customers retrieved successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Customer.class) 
	        						)
	        				}
	        ) })
	@GetMapping
	public List<Customer> findCustomers(@Parameter(name ="Email Addresss", example = "david@gmail.com", required = false) 
										@RequestParam(required = false, name ="emailAddress") String emailAddress,
										@Parameter(name ="Page Offset", description = "Used to filer number of items to return and pagination") 
										@RequestParam(required = false, defaultValue = "1" , name = "page") Integer pageNumber) {
		return service.findAllCustomer(emailAddress, pageNumber);
	}

	@Operation(summary = "Retrieve a single customer", description = "Retrieve a single customer with their bank accounts")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Customer single retrieved successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Customer.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
	        @ApiResponse(responseCode = "404", description = "Customer cannot be found or does not exists", content = @Content)
		})
	@GetMapping({"{id}", "/detail/{id}"})
	public Customer findCustomer(@PathVariable Long id) {
		return service.findCustomer(id);
	}

	@Operation(summary = "Create a new customer", description = "Create and save a single customer and assign a bank account to customer")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Customer created successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Customer.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to create customer provided", content = @Content),
		})
	@PostMapping(value = {"" , "/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Customer saveCustomer(@Valid @RequestBody CustomerDto customerDto) {
		return service.createCustomer(customerDto);
	}

	
	@Operation(summary = "Update an existing customer", description = "Used to update customer entity fields like full name or email address")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Customer updated successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Customer.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to update customer provided", content = @Content),
	        @ApiResponse(responseCode = "404", description = "Customer cannot be found or does not exists", content = @Content),
		})
	@PutMapping(value = {"{id}" , "/update/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDto customerUpdateDto) {
		return service.updateCustomer(id, customerUpdateDto);
	}
	
	@Operation(summary = "Delete an existing customer", description = "Delete an existing customer and all associated bank accounts")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Customer deleted successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Boolean.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid id parameter to delete customer provided", content = @Content),
	        @ApiResponse(responseCode = "404", description = "Customer cannot be found or does not exists", content = @Content),
		})
	@DeleteMapping({"{id}" , "/delete/{id}"})
	public boolean deleteCustomer(@PathVariable Long id) {
		return service.deleteCustomer(id);
	}
	
	
	@Operation(summary = "Delete an existing customer", description = "Delete an existing customer and all associated bank accounts")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Customer deleted successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Boolean.class) 
	        						)
	        				}
	        ),
	        @ApiResponse(responseCode = "400", description = "Invalid parameters to remove customer provided", content = @Content),
		})
	@PutMapping(value = {"/remove/many"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteManyCustomer(@Validated @RequestBody DeleteManyCustomerDto deleteManyCustomerDto) {
		return service.deleteManyCustomer(deleteManyCustomerDto);
	}
	
	
	@Operation(summary = "Delete all customer", description = "Delete all customer and all associated bank accounts")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "All Customer deleted successfully", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Boolean.class) 
	        						)
	        				}
	        )
		})
	@PutMapping(value = {"/remove/all"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteAllCustomer() {
		return service.deleteAllCustomer();
	}	
	
	@Operation(summary = "Check existences of a customer's email address", description = "When creating a new customer account, can be invoked to check whether email address can be used")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Email address available or unavailable as in true or false", 
	        		content = {
	        				@Content(
	        						mediaType = "application/json", 
	        						schema = @Schema(implementation = Boolean.class) 
	        						)
	        				}
	        )
		})
	@GetMapping("/exists/email/{email}")
	public boolean existsCustomerEmailAddress(@PathVariable(required = true, name = "email") String emailAddress) {
		return service.existCustomerEmail(emailAddress);
	}
	
}
