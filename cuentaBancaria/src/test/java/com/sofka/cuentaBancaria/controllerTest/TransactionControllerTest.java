package com.sofka.cuentaBancaria.controllerTest;

import com.sofka.cuentaBancaria.controller.TransactionController;
import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.model.TypeOfTransaction;
import com.sofka.cuentaBancaria.model.UserBalance;
import com.sofka.cuentaBancaria.service.TransactionHistoryService;
import com.sofka.cuentaBancaria.service.UserBalanceService;
import com.sofka.cuentaBancaria.service.UserService;
import com.sofka.cuentaBancaria.service.strategy.TypeTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionHistoryService transactionHistoryService;

    @MockBean
    private UserBalanceService userBalanceService;
    @MockBean
    private UserService userService;
    @MockBean
    private TypeTransaction typeTransaction;

    @Test
    public void createTransaction() throws Exception {
        TransactionHistory transactionHistory = new TransactionHistory();
        UserBalance userBalance = new UserBalance();

        transactionHistory.setUserId(1);
        transactionHistory.setTotal(new BigDecimal(100));
        transactionHistory.setTypeOfTransaction(TypeOfTransaction.BRANCH);

        userBalance.setBalance(new BigDecimal(100));

        when(userBalanceService.getUserBalance(transactionHistory.getUserId()))
                .thenReturn(userBalance);

        when(typeTransaction.movement(transactionHistory,userBalance)).thenReturn(new BigDecimal(200));

        doNothing().when(transactionHistoryService).createTransaction(transactionHistory);
        doNothing().when(userBalanceService).updateUserBalance(userBalance);

        String transactionJson = "{\"userId\": 1, \"total\": 100.0, \"typeOfTransaction\": \"BRANCH\"}";

        mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Transaction created succesfull"));
    }
}
