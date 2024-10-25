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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void initializateUser() throws Exception {
       user = new User();
       user.setUserId(1);
       user.setName("John");
    }

    @Test
    public void createUser() throws Exception {
        doNothing().when(userService).createUser(user);
        userService.createUser(user);

        String userJson = "{\"name\": \"John\"}";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully"));
        verify(userService).createUser(user);
    }

    @Test
    public void getUser() throws Exception {
        when(userService.getUser(1)).thenReturn(user);

         mockMvc.perform(get("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(content().string("{\"name\":\"John\",\"userId\":1}"));
    }
}
