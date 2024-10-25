package com.sofka.cuentaBancaria.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserBalance  {

    private int userId;
    private BigDecimal balance;

    public UserBalance(int userId,BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public UserBalance() {}
}
