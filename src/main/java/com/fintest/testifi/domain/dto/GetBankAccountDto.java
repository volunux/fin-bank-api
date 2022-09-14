package com.fintest.testifi.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.BankTransaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBankAccountDto {
	
	@Schema(description = "The bank account details",
			name ="bankAccount")
	private BankAccount bankAccount;

	@Schema(description = "The bank account associated transactions",
			name ="bankTransactions")
	private Set<BankTransaction> bankTransactions = new HashSet<BankTransaction>();
}
