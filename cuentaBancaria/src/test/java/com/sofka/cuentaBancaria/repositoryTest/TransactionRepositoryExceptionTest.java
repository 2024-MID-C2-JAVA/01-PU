package com.sofka.cuentaBancaria.repositoryTest;

import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.model.TypeOfTransaction;
import com.sofka.cuentaBancaria.repository.TransactionHistoryRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class TransactionRepositoryExceptionTest {

    @MockBean
    private TransactionHistoryRepositoryImpl repository;

    @Test
    public void createTransactionThrowSQLException() throws SQLException {
        TransactionHistory transaction = new TransactionHistory();
        transaction.setUserId(1);
        transaction.setTypeOfTransaction(TypeOfTransaction.ATM);
        transaction.setTotal(new BigDecimal(1200));

        doThrow(new SQLException("Database error")).when(repository).create(transaction);

        SQLException thrown = org.junit.jupiter.api.Assertions.assertThrows(SQLException.class, () -> {
            repository.create(transaction);
        });

        assertThat(thrown.getMessage()).isEqualTo("Database error");
    }
}
