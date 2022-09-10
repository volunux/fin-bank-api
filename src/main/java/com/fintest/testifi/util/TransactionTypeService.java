package com.fintest.testifi.util;

import com.fintest.testifi.domain.BankAccount;
import com.fintest.testifi.domain.other.BankTransactionType;

public class TransactionTypeService {

	public void performTransaction(BankAccount bankAccount, BankTransactionType bankTransactionType, Double amount, BankAccount otherAccount) {
		
		switch (bankTransactionType) {
			case BANK_FEE:
				performBankFee(bankAccount, amount);
			break;
			
			case CREDIT:
				performCredit(bankAccount, amount);
			break;
			
			case DEBIT:
				performDebit(bankAccount, amount);
			break;
			
			case DEPOSIT:
				performDeposit(bankAccount, amount);
			break;
			
			case TRANSFER:
				performTransfer(bankAccount, otherAccount, amount);
			break;
			
			default:
				performWithdrawal(bankAccount, amount);
		}
	}
	
	private void performWithdrawal(BankAccount bankAccount, Double amount) {
		double finalBalance = bankAccount.getBalance() - amount;
		bankAccount.setBalance(finalBalance);
	}
	
	private void performDeposit(BankAccount bankAccount, Double amount) {
		double finalBalance = bankAccount.getBalance() + amount;
		bankAccount.setBalance(finalBalance);
	}
	
	private void performDebit(BankAccount bankAccount, Double amount) {
		double finalBalance = bankAccount.getBalance() - amount;
		bankAccount.setBalance(finalBalance);
	}
	
	private void performCredit(BankAccount bankAccount, Double amount) {
		double finalBalance = bankAccount.getBalance() + amount;
		bankAccount.setBalance(finalBalance);
	}
	
	private void performBankFee(BankAccount bankAccount, Double amount) {
		double finalBalance = bankAccount.getBalance() - amount;
		bankAccount.setBalance(finalBalance);
	}
	
	private void performTransfer(BankAccount senderAccount, BankAccount recipientAccount, Double amount) {
		double finalSenderAccountBalance = senderAccount.getBalance() - amount;
		double finalRecipientAccountBalance = recipientAccount.getBalance() + amount;
		senderAccount.setBalance(finalSenderAccountBalance);
		recipientAccount.setBalance(finalRecipientAccountBalance);
	}
	
}
