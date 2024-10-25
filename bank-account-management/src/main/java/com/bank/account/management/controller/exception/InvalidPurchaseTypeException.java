package com.bank.account.management.controller.exception;

public class InvalidPurchaseTypeException extends RuntimeException {

    public InvalidPurchaseTypeException(String purchaseType) {
        super("Invalid purchase type: " + purchaseType);
    }
}
