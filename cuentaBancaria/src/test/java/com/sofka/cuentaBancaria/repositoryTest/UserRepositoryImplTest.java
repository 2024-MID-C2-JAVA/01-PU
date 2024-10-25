package com.sofka.cuentaBancaria.repositoryTest;

import com.sofka.cuentaBancaria.model.User;
import com.sofka.cuentaBancaria.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserRepositoryImplTest {

    @MockBean
    private UserRepositoryImpl userRepository;

    private User user;

    @BeforeEach
    public void initializeUser(){
        user = new User();
        user.setUserId(1);
        user.setName("John Doe");
    }

    @Test
    public void create() throws SQLException{
        doNothing().when(userRepository).create(user);
        userRepository.create(user);

        when(userRepository.getById(1)).thenReturn(user);
        Optional<User>result=Optional.ofNullable(userRepository.getById(1));

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(user.getName());
        verify(userRepository).create(user);
    }

    @Test
    public void update() throws SQLException {
        user.setName("Fulano");


        doNothing().when(userRepository).update(user);


        userRepository.update(user);


        when(userRepository.getById(user.getUserId())).thenReturn(user);


        Optional<User> result = Optional.ofNullable(userRepository.getById(user.getUserId()));


        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(user.getName());

        verify(userRepository).update(user);
    }

    @Test
    public void delete() throws SQLException {
        when(userRepository.delete(user)).thenReturn(true);

        userRepository.delete(user);

        when(userRepository.getById(user.getUserId())).thenReturn(null);

        Optional<User> result = Optional.ofNullable(userRepository.getById(user.getUserId()));

        assertThat(result).isEmpty();
        verify(userRepository).delete(user);
    }

    @Test
    public void getUser() throws SQLException {
        when(userRepository.getById(user.getUserId())).thenReturn(user);

        Optional<User> result = Optional.ofNullable(userRepository.getById(user.getUserId()));

        assertThat(result).isPresent();

        verify(userRepository).getById(user.getUserId());
    }
}
