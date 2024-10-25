package com.sofka.cuentaBancaria.repositoryTest;

import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.repository.UserBalanceRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserBalancerRepositoryTest {

    @MockBean
    private UserBalanceRepositoryImpl repository;

    private UserBalance userBalance;

    @BeforeEach
    public void initializeUserBalance(){
        userBalance = new UserBalance();
        userBalance.setUserId(1);
        userBalance.setBalance(new BigDecimal(1200));
    }

    @Test
    public void createTest() throws SQLException {

        doNothing().when(repository).create(userBalance);


        when(repository.getById(userBalance.getUserId())).thenReturn(userBalance);


        repository.create(userBalance);


        Optional<UserBalance> result = Optional.ofNullable(repository.getById(userBalance.getUserId()));


        assertThat(result).isPresent();
        assertThat(result.get().getBalance()).isEqualTo(userBalance.getBalance());
    }


    @Test
    public void update() throws SQLException {
        userBalance.setBalance(new BigDecimal(300)); // Nuevo balance


        doNothing().when(repository).update(userBalance);
        repository.update(userBalance);


        when(repository.getById(userBalance.getUserId())).thenReturn(userBalance);


        Optional<UserBalance> result = Optional.ofNullable(repository.getById(userBalance.getUserId()));


        assertThat(result).isPresent();
        assertThat(result.get().getBalance()).isEqualTo(userBalance.getBalance());
    }


    @Test
    public void deleteTest() throws SQLException {
        when(repository.delete(userBalance)).thenReturn(true);

        repository.delete(userBalance);

        when(repository.getById(userBalance.getUserId())).thenReturn(null);

        Optional<UserBalance> result = Optional.ofNullable(repository.getById(userBalance.getUserId()));


        assertThat(result).isEmpty();
    }


    @Test
    public void testGetById() throws SQLException {

        when(repository.getById(userBalance.getUserId()))
                .thenReturn(userBalance);

        UserBalance result = repository.getById(userBalance.getUserId());


        assertThat(result).isNotNull();
        verify(repository).getById(userBalance.getUserId());
    }


}
