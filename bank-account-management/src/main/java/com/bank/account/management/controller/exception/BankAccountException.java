package com.bank.account.management.controller.exception;

public class BankAccountException extends RuntimeException {

    public BankAccountException(String message) {
        super(message);
    }

    public static class BankAccountNotFoundException extends CustomerException {
        public BankAccountNotFoundException() {
            super("Account not found");
        }
    }

    public static class BankAccountAlreadyExistsException extends CustomerException {
        public BankAccountAlreadyExistsException(String username) {
            super("Account already exists.");
        }
    }
}
