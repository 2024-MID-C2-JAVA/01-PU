package com.bank.account.management.controller.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("invalid amount");
    }
}