package com.bank.account.management.controller.exception;

public class InvalidDepositTypeException extends RuntimeException {
    public InvalidDepositTypeException(String type) {
        super("Invalid deposit type: " + type);
    }
}