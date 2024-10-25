package com.bank.account.management.controller.exception;

public class InsufficientFundsException extends RuntimeException {


    public InsufficientFundsException() {
        super("Insufficient funds in the account.");
    }
}
