package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.services.AccountService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AccountDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountShouldReturnOkWhenAccountExists() throws Exception {
        AccountDto mockAccountDto = new AccountDto(
                new BigDecimal("100.0"), new TransactionDto[]{}
        );
        when(accountService.getAccount()).thenReturn(mockAccountDto);

        mockMvc.perform(get("/account/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("100.0"))
                .andExpect(jsonPath("$.transactions").isArray());
    }

    @Test
    void getAccountShouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        when(accountService.getAccount()).thenReturn(null);

        mockMvc.perform(get("/account/")
                .contentType(MediaType.APPLICATION_JSON));
    }
}
