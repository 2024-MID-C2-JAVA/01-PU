package com.bank.account.management.controller.exception;

public class AccountNotBelongsToCustomerException extends RuntimeException {
    public AccountNotBelongsToCustomerException() {
        super("Account does not belong to the specified customer.");
    }
}