package com.sofka.cuentaBancaria.service;

import com.sofka.cuentaBancaria.config.DatabaseConnection;
import com.sofka.cuentaBancaria.model.User;
import com.sofka.cuentaBancaria.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    private UserRepositoryImpl repository;

    @Autowired
    public UserService(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    public void createUser(User user) throws SQLException {
        repository.create(user);
    }

    public User getUser(int id) throws SQLException {
        return repository.getById(id);
    }
}
