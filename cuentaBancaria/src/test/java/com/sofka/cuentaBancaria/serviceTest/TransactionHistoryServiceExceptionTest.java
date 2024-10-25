package com.sofka.cuentaBancaria.serviceTest;

import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.model.TypeOfTransaction;
import com.sofka.cuentaBancaria.repository.TransactionHistoryRepositoryImpl;
import com.sofka.cuentaBancaria.service.TransactionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionHistoryServiceExceptionTest {

    @Mock
    private TransactionHistoryRepositoryImpl repository;

    @InjectMocks
    private TransactionHistoryService transactionHistoryService;

    private TransactionHistory transactionHistory;

    @BeforeEach
    public void initializeTransaction() {
        transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(1);
        transactionHistory.setUserId(1);
        transactionHistory.setTotal(new BigDecimal(100));
        transactionHistory.setTypeOfTransaction(TypeOfTransaction.BRANCH);
    }

    @Test
    public void createTransactionThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(repository).create(any(TransactionHistory.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            transactionHistoryService.createTransaction(transactionHistory);
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void getTransactionThrowSQLException() throws SQLException {
        when(repository.getById(transactionHistory.getTransactionId())).thenThrow(new SQLException("Database error"));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            transactionHistoryService.getTransaction(transactionHistory);
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void getAllTransactionsThrowSQLException() throws SQLException {
        when(repository.getAll()).thenThrow(new SQLException("Database error"));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            transactionHistoryService.getAllTransactions();
        });

        assertEquals("Database error", thrown.getMessage());
    }
}
