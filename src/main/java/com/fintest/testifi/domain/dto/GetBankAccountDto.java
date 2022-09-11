package com.fintest.testifi.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.BankTransaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBankAccountDto {
	
	private BankAccount bankAccount;
	private Set<BankTransaction> bankTransactions = new HashSet<BankTransaction>();
}
