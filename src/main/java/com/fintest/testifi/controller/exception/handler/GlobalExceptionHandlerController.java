package com.fintest.testifi.controller.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fintest.testifi.domain.exception.BankAccountDuplicateEntityException;
import com.fintest.testifi.domain.exception.BankAccountLockedException;
import com.fintest.testifi.domain.exception.BankAccountNotFoundException;
import com.fintest.testifi.domain.exception.BankAccountPinException;
import com.fintest.testifi.domain.exception.CustomerDuplicateEntityException;
import com.fintest.testifi.domain.exception.CustomerNotFoundException;
import com.fintest.testifi.domain.exception.InsufficientBankAccountBalanceException;
import com.fintest.testifi.domain.exception.InvalidBankTransferTransaction;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<String, Object>();
		Map<String, String> errMap = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach(new Consumer<ObjectError>() {
			@Override
			public void accept(ObjectError error) {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errMap.put(fieldName, errorMessage);
			}
		});	
		errors.put("message", errMap);
		errors.put("errorType", "dataValidation");
		return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Object handleDoctorNotFound(DataIntegrityViolationException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("entityName", "Unknown");
		errors.put("message", "One of fields submitted matches that of an exisiting entity or the referenced entity id does not exist, all existent and new entities field must be unique and referenced ids must exist.");
		errors.put("code" , Integer.toString(HttpStatus.BAD_REQUEST.value()));
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CustomerNotFoundException.class)
	public Object handleNotFoundEntity(CustomerNotFoundException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("entityName", CustomerNotFoundException.ENTITY_NAME);
		errors.put("message", ex.getMessage());
		ex.setCode(HttpStatus.NOT_FOUND.value());
		errors.put("code", ex.getCode().toString());
		return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CustomerDuplicateEntityException.class)
	public Object handleCustomerDuplicateEntity(CustomerDuplicateEntityException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("entityName", CustomerDuplicateEntityException.ENTITY_NAME);
		errors.put("message", ex.getMessage());
		ex.setCode(HttpStatus.BAD_REQUEST.value());
		errors.put("code", ex.getCode().toString());
		return errors;
	}
	
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  @ExceptionHandler(BankAccountNotFoundException.class)
	  public Object handleNotFoundEntity(BankAccountNotFoundException ex) {
	    Map<String, String> errors = new HashMap<String, String>();
	    errors.put("entityName", BankAccountNotFoundException.ENTITY_NAME);
	    errors.put("message", ex.getMessage());
	    ex.setCode(HttpStatus.NOT_FOUND.value());
	    errors.put("code", ex.getCode().toString());
	    return errors;
	  }
	  
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(BankAccountDuplicateEntityException.class)
	  public Object handleDuplicateEntity(BankAccountDuplicateEntityException ex) {
	    Map<String, String> errors = new HashMap<String, String>();
	    errors.put("entityName", BankAccountDuplicateEntityException.ENTITY_NAME);
	    errors.put("message", ex.getMessage());
	    ex.setCode(HttpStatus.BAD_REQUEST.value());
	    errors.put("code", ex.getCode().toString());
	    return errors;
	  }
	  
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(InsufficientBankAccountBalanceException.class)
	  public Object handleInsufficientBalance(InsufficientBankAccountBalanceException ex) {
	    Map<String, String> errors = new HashMap<String, String>();
	    errors.put("entityName", InsufficientBankAccountBalanceException.ENTITY_NAME);
	    errors.put("message", ex.getMessage());
	    ex.setCode(HttpStatus.BAD_REQUEST.value());
	    errors.put("code", ex.getCode().toString());
	    return errors;
	  }
	  
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(BankAccountPinException.class)
	  public Object handleAccountPin(BankAccountPinException ex) {
	    Map<String, String> errors = new HashMap<String, String>();
	    errors.put("entityName", BankAccountPinException.ENTITY_NAME);
	    errors.put("message", ex.getMessage());
	    ex.setCode(HttpStatus.BAD_REQUEST.value());
	    errors.put("code", ex.getCode().toString());
	    return errors;
	  }
	  
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(BankAccountLockedException.class)
	  public Object handleAccountLocked(BankAccountLockedException ex) {
	    Map<String, String> errors = new HashMap<String, String>();
	    errors.put("entityName", BankAccountLockedException.ENTITY_NAME);
	    errors.put("message", ex.getMessage());
	    ex.setCode(HttpStatus.BAD_REQUEST.value());
	    errors.put("code", ex.getCode().toString());
	    return errors;
	  }
	  
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(InvalidBankTransferTransaction.class)
	  public Object handleInvalidTransferTransaction(InvalidBankTransferTransaction ex) {
	    Map<String, String> errors = new HashMap<String, String>();
	    errors.put("entityName", InvalidBankTransferTransaction.ENTITY_NAME);
	    errors.put("message", ex.getMessage());
	    ex.setCode(HttpStatus.BAD_REQUEST.value());
	    errors.put("code", ex.getCode().toString());
	    return errors;
	  }
	
}
