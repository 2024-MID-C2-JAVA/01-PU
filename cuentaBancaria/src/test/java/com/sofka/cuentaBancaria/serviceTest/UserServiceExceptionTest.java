package com.sofka.cuentaBancaria.serviceTest;

import com.sofka.cuentaBancaria.model.User;
import com.sofka.cuentaBancaria.repository.UserRepositoryImpl;
import com.sofka.cuentaBancaria.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceExceptionTest {

    @Mock
    private UserRepositoryImpl repository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void initializeUser() {
        user = new User();
        user.setUserId(1);
        user.setName("John Doe");
    }

    @Test
    public void createUserThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(repository).create(any(User.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void getUserThrowSQLException() throws SQLException {
        when(repository.getById(user.getUserId())).thenThrow(new SQLException("Database error"));


        SQLException thrown = assertThrows(SQLException.class, () -> {
            userService.getUser(user.getUserId());
        });

        assertEquals("Database error", thrown.getMessage());
    }
}
