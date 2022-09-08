package com.fintest.testifi.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fintest.testifi.validator.impl.BankAccountMinimumAgeValidatorImpl;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BankAccountMinimumAgeValidatorImpl.class)
public @interface BankAccountMinimumAgeValidator {
	
	int age() default 18;

	String message() default "";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}
