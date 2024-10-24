package com.bank.account.management.model.dto;

import java.math.BigDecimal;

public class WithdrawalDTO {
    private Long customerId;
    private String accountNumber;
    private BigDecimal amount;

    public WithdrawalDTO() {}

    public WithdrawalDTO(Long customerId, String accountNumber, BigDecimal amount) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    // Getters y setters
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
}
