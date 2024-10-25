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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionHistoryServiceTest {

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
    public void createTransaction() throws SQLException {
        doNothing().when(repository).create(any(TransactionHistory.class));

        transactionHistoryService.createTransaction(transactionHistory);

        verify(repository, times(1)).create(transactionHistory);
    }

    @Test
    public void getTransaction() throws SQLException {
        when(repository.getById(transactionHistory.getTransactionId())).thenReturn(transactionHistory);

        TransactionHistory result = transactionHistoryService.getTransaction(transactionHistory);

        verify(repository, times(1)).getById(transactionHistory.getTransactionId()); // Verifica que se llamó al método getById
        assertEquals(transactionHistory, result);
    }

    @Test
    public void getAllTransactions_ShouldReturnListOfTransactions() throws SQLException {
        List<TransactionHistory> transactionList = new ArrayList<>();
        transactionList.add(transactionHistory);

        when(repository.getAll()).thenReturn(transactionList);

        List<TransactionHistory> result = transactionHistoryService.getAllTransactions();

        verify(repository, times(1)).getAll();
        assertEquals(transactionList, result);
    }
}
