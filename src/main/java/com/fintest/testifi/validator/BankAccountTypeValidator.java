package com.fintest.testifi.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fintest.testifi.validator.impl.BankAccountTypeValidatorImpl;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BankAccountTypeValidatorImpl.class)
public @interface BankAccountTypeValidator {

	Class<? extends Enum<?>> enumClass();
	
	String message() default "";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
