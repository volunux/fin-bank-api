package com.fintest.testifi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Schema;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Inherited
@Schema(description ="The customer's full name",
	name = "fullName",
	required = true,
	minLength = 1,
	maxLength = 150,
	example = "David Bareth" )
public @interface FullNameSchema {

}
