package com.fintest.testifi.validator.impl;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fintest.testifi.validator.BankAccountMinimumAgeValidator;

public class BankAccountMinimumAgeValidatorImpl implements ConstraintValidator<BankAccountMinimumAgeValidator, Date> {

	@Override
	public void initialize(BankAccountMinimumAgeValidator annotation) {

	}
	
	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int year = calendar.get(Calendar.YEAR);

		if ((currentYear - year) >= 18) {
			return true;
		}
		else {
			return false;
		}
	}

}
