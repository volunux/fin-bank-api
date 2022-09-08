package com.fintest.testifi.util;

import java.beans.FeatureDescriptor;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fintest.testifi.domain.other.BankAccountStatus;
import com.fintest.testifi.domain.other.BankAccountType;

public class FinBankUtil {

	public static String[] getNullPropertyNames(Object source) {
	    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	    return Stream.of(wrappedSource.getPropertyDescriptors())
	        .map(FeatureDescriptor ::getName)
	        .filter(propertyName -> {
	            try {
	               return Objects.isNull(wrappedSource.getPropertyValue(propertyName));
	            } catch (Exception e) {
	               return false;
	            }                
	        })
	        .toArray(String[]::new);
	}
	
	public static final BankAccountType getBankAccountType(String bankAccountType) {
		
		switch (bankAccountType) {
			case "saving":
				return BankAccountType.SAVING;
				
			case "current":
				return BankAccountType.CURRENT;
			
			case "cheque":
				return BankAccountType.CHEQUE;
			
			default:
				return BankAccountType.SAVING;
		}
	}
	
	public static final BankAccountStatus getBankAccountStatus(String bankAccountStatus) {
		
		switch (bankAccountStatus) {
			case "active":
				return BankAccountStatus.ACTIVE;
				
			case "inactive":
				return BankAccountStatus.INACTIVE;
			
			case "disabled":
				return BankAccountStatus.DISABLED;
			
			default:
				return BankAccountStatus.ACTIVE;
		}
	}

}
