package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.controllers.PurchasesController;
import co.com.sofka.sofkau.cuentaflex.services.TransactionsService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AddTransactionDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import co.com.sofka.sofkau.cuentaflex.models.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchasesController.class)
class PurchasesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @InjectMocks
    private PurchasesController purchasesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerPhysicalPurchaseShouldReturnTransactionResponse() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("100.0"));
        TransactionDoneResponseDto responseDto = new TransactionDoneResponseDto("Transaction was successful!", new BigDecimal("90.0"));

        when(transactionsService.processPhysicalPurchase(requestBody.amount())).thenReturn(responseDto);

        mockMvc.perform(post("/account/physical-purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Transaction was successful!\",\"balance\":90.0}"));
    }

    @Test
    void registerOnlinePurchaseShouldReturnTransactionResponse() throws Exception {
        AddTransactionDto requestBody = new AddTransactionDto(new BigDecimal("50.0"));
        TransactionDoneResponseDto responseDto = new TransactionDoneResponseDto("Transaction was successful!", new BigDecimal("45.0"));

        when(transactionsService.processOnlinePurchase(requestBody.amount())).thenReturn(responseDto);

        mockMvc.perform(post("/account/online-purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Transaction was successful!\",\"balance\":45.0}"));
    }
}
