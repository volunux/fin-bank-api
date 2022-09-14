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
@Schema(description = "The customer's address",
	name ="homeAddress",
	required = true,
	minLength = 1,
	maxLength = 200,
	example = "London, United Kingdom")
public @interface HomeAddressSchema {

}
