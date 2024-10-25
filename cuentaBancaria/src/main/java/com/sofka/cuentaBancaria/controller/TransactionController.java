package com.sofka.cuentaBancaria.controller;

import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.service.TransactionHistoryService;
import com.sofka.cuentaBancaria.service.UserBalanceService;
import com.sofka.cuentaBancaria.service.UserService;
import com.sofka.cuentaBancaria.service.strategy.AccountMovementContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionHistoryService transactionHistoryService;
    private final UserBalanceService userBalanceService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionHistoryService transactionHistoryService, UserBalanceService userBalanceService, UserService userService) {
        this.transactionHistoryService = transactionHistoryService;
        this.userBalanceService = userBalanceService;
        this.userService = userService;
    }

    public void updateBalance(UserBalance userBalance) throws SQLException {
        userBalanceService.updateUserBalance(userBalance);
    }

    @PostMapping
    public ResponseEntity<String>createTransaction(@RequestBody TransactionHistory transactionHistory){
        try{
            UserBalance userBalance=userBalanceService.getUserBalance(transactionHistory.getUserId());
            userBalance.setBalance(AccountMovementContext.accountMovement(transactionHistory).movement(transactionHistory,userBalance));

            transactionHistoryService.createTransaction(transactionHistory);

            updateBalance(userBalance);

            return ResponseEntity.status(HttpStatus.CREATED).body("Transaction created succesful: "+userBalance.getBalance());
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }



}
