package com.bank.account.management.controller.exception;

public class CustomerException extends RuntimeException {

    public CustomerException(String message) {
        super(message);
    }

    public static class CustomerNotFoundException extends CustomerException {
        public CustomerNotFoundException(Long id) {
            super("Customer with ID " + id + " doesn't exist.");
        }
    }

    public static class CustomerAlreadyExistsException extends CustomerException {
        public CustomerAlreadyExistsException(String username) {
            super("Customer with username " + username + " already exists.");
        }
    }
}
