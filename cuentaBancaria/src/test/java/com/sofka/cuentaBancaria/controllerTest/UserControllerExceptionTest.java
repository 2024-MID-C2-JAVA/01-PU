package com.sofka.cuentaBancaria.controllerTest;

import com.sofka.cuentaBancaria.controller.UserController;
import com.sofka.cuentaBancaria.model.User;
import com.sofka.cuentaBancaria.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void initializateUser() {
        user = new User();
        user.setUserId(1);
        user.setName("John");
    }

    @Test
    public void createUserInternalServerError() throws Exception {
        doThrow(new SQLException("Database error"))
                .when(userService)
                .createUser(any(User.class));

        String userJson = "{\"name\": \"John\"}";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error creating user"));
    }
}
