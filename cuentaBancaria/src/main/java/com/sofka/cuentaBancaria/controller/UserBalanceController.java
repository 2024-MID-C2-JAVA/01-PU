package com.sofka.cuentaBancaria.controller;

import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/api/balance")
public class UserBalanceController {

    private final UserBalanceService userBalanceService;

    @Autowired
    public UserBalanceController(UserBalanceService userBalanceService) {
        this.userBalanceService = userBalanceService;
    }

    @PostMapping
    public ResponseEntity<String> createBalance(@RequestBody UserBalance userBalance) {
        try{
            userBalanceService.createUserBalance(userBalance);
            return ResponseEntity.status(HttpStatus.CREATED).body("Balance created succesful");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating balance");
        }
    }
}
