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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

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
    public void createUser() throws SQLException {
        doNothing().when(repository).create(any(User.class));

        userService.createUser(user);

        verify(repository, times(1)).create(user);
    }

    @Test
    public void getUser() throws SQLException {
        when(repository.getById(user.getUserId())).thenReturn(user);

        User result = userService.getUser(user.getUserId());

        verify(repository, times(1)).getById(user.getUserId());
        assertEquals(user, result);
    }
}
