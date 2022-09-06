package com.fintest.testifi.util;

import java.beans.FeatureDescriptor;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

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
	

}
