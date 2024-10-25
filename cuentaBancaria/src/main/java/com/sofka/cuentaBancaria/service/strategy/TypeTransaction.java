package com.sofka.cuentaBancaria.service.strategy;

import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.model.UserBalance;

import java.math.BigDecimal;

public interface TypeTransaction {
    BigDecimal movement(TransactionHistory transactionHistory, UserBalance userBalance);
}
