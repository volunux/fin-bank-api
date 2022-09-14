package com.fintest.testifi.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Schema;

@Retention(RUNTIME)
@Target(FIELD)
@Inherited
@Schema(description ="The customer's phone number",
	name = "phoneNumber",
	required = true,
	minLength = 1,
	maxLength = 15,
	example = "+2349022035588" )
public @interface PhoneNumberSchema {

}
