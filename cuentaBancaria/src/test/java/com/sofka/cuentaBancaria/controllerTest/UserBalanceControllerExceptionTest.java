package com.sofka.cuentaBancaria.controllerTest;

import com.sofka.cuentaBancaria.controller.UserBalanceController;
import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.service.UserBalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserBalanceController.class)
public class UserBalanceControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBalanceService userBalanceService;

    private UserBalance userBalance;

    @BeforeEach
    public void initializateUserBalance() {
        userBalance = new UserBalance();
        userBalance.setUserId(1);
        userBalance.setBalance(new BigDecimal("100.0"));
    }

    @Test
    public void createBalanceInternalServerError() throws Exception {
        doThrow(new SQLException("Database error")).when(userBalanceService).createUserBalance(any(UserBalance.class));

        String balanceJson = "{\"userId\": 1, \"balance\": 100.0}";

        mockMvc.perform(post("/api/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(balanceJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error creating balance"));
    }

}
