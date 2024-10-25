package com.sofka.cuentaBancaria.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionHistory {

    private int transactionId;
    private int userId;
    private BigDecimal total;
    private TypeOfTransaction typeOfTransaction;

    public TransactionHistory(int userId, BigDecimal total, TypeOfTransaction typeOfTransaction) {
        this.userId = userId;
        this.total = total;
        this.typeOfTransaction = typeOfTransaction;
    }

    public TransactionHistory(){}
}
