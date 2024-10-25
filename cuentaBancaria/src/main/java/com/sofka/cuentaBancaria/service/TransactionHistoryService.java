package com.sofka.cuentaBancaria.service;

import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.repository.TransactionHistoryRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TransactionHistoryService {

    private final TransactionHistoryRepositoryImpl repository;

    @Autowired
    public TransactionHistoryService(TransactionHistoryRepositoryImpl repository) {
        this.repository = repository;
    }

    public void createTransaction(TransactionHistory transactionHistory) throws SQLException {
        repository.create(transactionHistory);
    }

    public TransactionHistory getTransaction(TransactionHistory transactionHistory) throws SQLException {
        return repository.getById(transactionHistory.getTransactionId());
    }

    public List<TransactionHistory>getAllTransactions() throws SQLException {
        return repository.getAll();
    }
}


