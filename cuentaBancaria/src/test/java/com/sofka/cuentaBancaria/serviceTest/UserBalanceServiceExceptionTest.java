package com.sofka.cuentaBancaria.serviceTest;

import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.repository.UserBalanceRepositoryImpl;
import com.sofka.cuentaBancaria.service.UserBalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserBalanceServiceExceptionTest {
    @Mock
    private UserBalanceRepositoryImpl repository;

    @InjectMocks
    private UserBalanceService userBalanceService;

    private UserBalance userBalance;

    @BeforeEach
    public void initializeUserBalance() {
        userBalance = new UserBalance();
        userBalance.setUserId(1);
        userBalance.setBalance(new BigDecimal(1000));
    }

    @Test
    public void createUserBalanceSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(repository).create(any(UserBalance.class));

        assertThrows(SQLException.class, () -> userBalanceService.createUserBalance(userBalance));
    }

    @Test
    public void updateUserBalanceSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(repository).update(any(UserBalance.class));

        assertThrows(SQLException.class, () -> userBalanceService.updateUserBalance(userBalance));
    }

    @Test
    public void getUserBalanceSQLException() throws SQLException {
        when(repository.getById(userBalance.getUserId())).thenThrow(new SQLException("Database error"));

        assertThrows(SQLException.class, () -> userBalanceService.getUserBalance(userBalance.getUserId()));
    }

    @Test
    public void getAllUserBalanceSQLException() throws SQLException {
        when(repository.getAll()).thenThrow(new SQLException("Database error"));

        assertThrows(SQLException.class, () -> userBalanceService.getAllUserBalance());
    }
}
