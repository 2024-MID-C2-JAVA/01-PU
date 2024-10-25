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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserBalanceServiceTest {

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
    public void createUserBalance() throws SQLException {
        doNothing().when(repository).create(any(UserBalance.class));

        userBalanceService.createUserBalance(userBalance);

        verify(repository, times(1)).create(userBalance);
    }

    @Test
    public void updateUserBalance() throws SQLException {
        doNothing().when(repository).update(any(UserBalance.class));

        userBalanceService.updateUserBalance(userBalance);

        verify(repository, times(1)).update(userBalance);
    }

    @Test
    public void getUserBalance() throws SQLException {
        when(repository.getById(userBalance.getUserId())).thenReturn(userBalance);

        UserBalance result = userBalanceService.getUserBalance(userBalance.getUserId());

        verify(repository, times(1)).getById(userBalance.getUserId());
        assertEquals(userBalance, result);
    }

    @Test
    public void getAllUserBalance() throws SQLException {
        List<UserBalance> userBalanceList = new ArrayList<>();
        userBalanceList.add(userBalance);

        when(repository.getAll()).thenReturn(userBalanceList);

        List<UserBalance> result = userBalanceService.getAllUserBalance();

        verify(repository, times(1)).getAll();
        assertEquals(userBalanceList, result);
    }

}
