package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.services.TransactionsService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AddTransactionDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import co.com.sofka.sofkau.cuentaflex.services.exceptions.MinimumAmountNotReachedException;
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

@WebMvcTest(DepositsController.class)
class DepositsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerBranchDepositShouldReturnOk() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("200.00"));
        TransactionDoneResponseDto responseDto = new TransactionDoneResponseDto("Transaction was successful!", new BigDecimal("200.0"));

        when(transactionsService.processBranchDeposit(requestBody.amount())).thenReturn(responseDto);

        mockMvc.perform(post("/account/branch-deposits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 200.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transaction was successful!"))
                .andExpect(jsonPath("$.balance").value("200.0"));
    }

    @Test
    void registerAtmDepositShouldReturnOk() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("150.00"));
        TransactionDoneResponseDto responseDto = new TransactionDoneResponseDto("Transaction was successful!", new BigDecimal("150.0"));

        when(transactionsService.processAtmDeposit(requestBody.amount())).thenReturn(responseDto);

        mockMvc.perform(post("/account/atm-deposits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":150.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transaction was successful!"))
                .andExpect(jsonPath("$.balance").value("150.0"));
    }

    @Test
    void registerExternalDepositShouldReturnOk() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("300.00"));
        TransactionDoneResponseDto responseDto = new TransactionDoneResponseDto("Transaction was successful!", new BigDecimal("300.00"));

        when(transactionsService.processExternalDeposit(requestBody.amount())).thenReturn(responseDto);

        mockMvc.perform(post("/account/external-deposits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":300.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transaction was successful!"))
                .andExpect(jsonPath("$.balance").value("300.0"));
    }

    @Test
    void registerBranchDepositShouldReturnBadRequestWhenMinimumAmountNotReached() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("5.00"));

        when(transactionsService.processBranchDeposit(requestBody.amount()))
                .thenThrow(new MinimumAmountNotReachedException("The amount for this transaction should be greater than $10.00"));

        mockMvc.perform(post("/account/branch-deposits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":5.00}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("The amount for this transaction should be greater than $10.00"));
    }
}
