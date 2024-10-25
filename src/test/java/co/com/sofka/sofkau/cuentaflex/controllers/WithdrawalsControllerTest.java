package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.services.TransactionsService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AddTransactionDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import co.com.sofka.sofkau.cuentaflex.models.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WithdrawalsController.class)
class WithdrawalsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerAtmWithdrawalShouldReturnTransactionResponse() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("150.00"));
        TransactionDoneResponseDto responseDto = new TransactionDoneResponseDto("Transaction was successful!", new BigDecimal("850.00"));

        when(transactionsService.processAtmWithdrawal(requestBody.amount())).thenReturn(responseDto);

        mockMvc.perform(post("/account/atm-withdrawals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 150.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transaction was successful!"))
                .andExpect(jsonPath("$.balance").value(850.00));
    }

    @Test
    void registerAtmWithdrawalShouldHandleInsufficientBalanceException() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("300.00"));

        when(transactionsService.processAtmWithdrawal(requestBody.amount())).thenThrow(new InsufficientBalanceException("Insufficient balance for this transaction"));

        mockMvc.perform(post("/account/atm-withdrawals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 300.00}"))
                .andExpect(status().isBadRequest());
    }
}
