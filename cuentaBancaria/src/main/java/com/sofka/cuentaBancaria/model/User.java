package com.sofka.cuentaBancaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int UserId;
    private String name;

    public User(int UserId, String name) {
        this.UserId = UserId;
        this.name = name;
    }

    public User() {
    }
}
