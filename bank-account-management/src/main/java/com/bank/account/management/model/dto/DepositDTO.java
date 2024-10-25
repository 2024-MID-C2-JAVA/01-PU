package com.bank.account.management.model.dto;

import java.math.BigDecimal;

public class DepositDTO {
    private Long customerId;
    private String accountNumber;
    private BigDecimal amount;
    private String type;

    public DepositDTO() {}

    public DepositDTO(Long customerId, String accountNumber, BigDecimal amount, String type) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
    }

    // Getters and setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
