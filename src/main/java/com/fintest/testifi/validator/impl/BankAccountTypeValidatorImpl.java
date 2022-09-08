package com.fintest.testifi.validator.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fintest.testifi.validator.BankAccountTypeValidator;

public class BankAccountTypeValidatorImpl implements ConstraintValidator<BankAccountTypeValidator, CharSequence> {
	
	private List<String> acceptedValues = new ArrayList<String>();

	@Override
	public void initialize(BankAccountTypeValidator annotation) {
		for (Enum<?> enumValue : annotation.enumClass().getEnumConstants()) {
			acceptedValues.add(enumValue.toString());
		}
	}
	
	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		return acceptedValues.contains(value.toString());
	}
}
