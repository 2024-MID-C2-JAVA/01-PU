package com.sofka.cuentaBancaria.repositoryTest;

import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.repository.UserBalanceRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserBalanceRepositoryExceptionTest {

    @MockBean
    private UserBalanceRepositoryImpl repository;

    private UserBalance userBalance;

    @BeforeEach
    public void initializeUserBalance() {
        userBalance = new UserBalance();
        userBalance.setUserId(1);
        userBalance.setBalance(new BigDecimal(1200));
    }

    @Test
    public void createThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(repository).create(Mockito.any(UserBalance.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            repository.create(userBalance);
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void updateThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(repository).update(Mockito.any(UserBalance.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            repository.update(userBalance);
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void getByIdThrowSQLException() throws SQLException {
        when(repository.getById(userBalance.getUserId())).thenThrow(new SQLException("Database error"));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            repository.getById(userBalance.getUserId());
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void deleteThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(repository).delete(Mockito.any(UserBalance.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            repository.delete(userBalance);
        });

        assertEquals("Database error", thrown.getMessage());
    }
}
