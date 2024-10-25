package com.sofka.cuentaBancaria.repositoryTest;

import com.sofka.cuentaBancaria.model.User;
import com.sofka.cuentaBancaria.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserRepositoryExceptionTest {

    @MockBean
    private UserRepositoryImpl userRepository;

    private User user;

    @BeforeEach
    public void initializeUser() {
        user = new User();
        user.setUserId(1);
        user.setName("John Doe");
    }

    @Test
    public void createThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(userRepository).create(Mockito.any(User.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            userRepository.create(user);
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void updateThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(userRepository).update(Mockito.any(User.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            userRepository.update(user);
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void getByIdThrowSQLException() throws SQLException {
        when(userRepository.getById(user.getUserId())).thenThrow(new SQLException("Database error"));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            userRepository.getById(user.getUserId());
        });

        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    public void deletThrowSQLException() throws SQLException {
        doThrow(new SQLException("Database error")).when(userRepository).delete(Mockito.any(User.class));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            userRepository.delete(user);
        });

        assertEquals("Database error", thrown.getMessage());
    }
}
