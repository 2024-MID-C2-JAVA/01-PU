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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserBalanceController.class)
public class UserBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBalanceService userBalanceService;

    private UserBalance userBalance;

    @BeforeEach
    public void initializateUserBalance() throws Exception {
        userBalance = new UserBalance();
        userBalance.setUserId(1);
        userBalance.setBalance(new BigDecimal("100.0"));
    }


    @Test
    public void createBalance() throws Exception {
        doNothing().when(userBalanceService).createUserBalance(userBalance);
        userBalanceService.createUserBalance(userBalance);

        String balanceJson = "{\"userId\": 1, \"balance\": 100.0}";

        mockMvc.perform(post("/api/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(balanceJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Balance created successfully"));
        verify(userBalanceService).createUserBalance(userBalance);
    }


}
