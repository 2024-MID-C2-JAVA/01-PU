package com.sofka.cuentaBancaria.service;

import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.repository.UserBalanceRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserBalanceService {

    private UserBalanceRepositoryImpl repository;

    @Autowired
    public UserBalanceService(UserBalanceRepositoryImpl repository) {
        this.repository = repository;
    }

    public void createUserBalance(UserBalance userBalance) throws SQLException {
        repository.create(userBalance);
    }

    public void updateUserBalance(UserBalance userBalance) throws SQLException {
        repository.update(userBalance);
    }

    public UserBalance getUserBalance(int id) throws SQLException {
        return repository.getById(id);
    }

    public List<UserBalance> getAllUserBalance() throws SQLException {
        return repository.getAll();
    }
}
