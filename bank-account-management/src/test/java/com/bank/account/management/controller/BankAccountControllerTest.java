package com.bank.account.management.controller;

import com.bank.account.management.controller.exception.*;
import com.bank.account.management.model.dto.BankAccountDTO;
import com.bank.account.management.model.dto.DepositDTO;
import com.bank.account.management.model.dto.PurchaseDTO;
import com.bank.account.management.model.dto.WithdrawalDTO;
import com.bank.account.management.service.IBankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBankAccountService bankAccountService;



    @Test
    void createAccount_shouldReturnCreatedBankAccount() throws Exception {
        // Arrange
        BankAccountDTO createdAccount = new BankAccountDTO(1L, 1L, "123456789", BigDecimal.valueOf(1000.0));
        when(bankAccountService.createAccount(any(BankAccountDTO.class))).thenReturn(createdAccount);

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"accountNumber\":\"123456789\",\"balance\":1000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value("123456789"))
                .andExpect(jsonPath("$.balance").value(1000.0));

        verify(bankAccountService, times(1)).createAccount(any(BankAccountDTO.class));
    }

    @Test
    void createAccount_shouldReturnBadRequest_whenIdIsPresent() throws Exception {
        // Arrange
        String jsonWithId = "{\"id\":1,\"customerId\":1,\"accountNumber\":\"123456789\",\"balance\":1000.0}";

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithId))
                .andExpect(status().isBadRequest());

        // Verify that the service method was not called
        verify(bankAccountService, times(0)).createAccount(any(BankAccountDTO.class));
    }

    @Test
    void createAccount_shouldReturnBadRequest_whenCustomerIdIsMissing() throws Exception {
        // Arrange
        String jsonWithoutCustomerId = "{\"accountNumber\":\"123456789\",\"balance\":1000.0}"; // missing "customerId"

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithoutCustomerId))
                .andExpect(status().isBadRequest()); // Expecting 400 Bad Request

        // Verify that the service method was not called
        verify(bankAccountService, times(0)).createAccount(any(BankAccountDTO.class));
    }

    @Test
    void createAccount_shouldReturnBadRequest_whenAccountNumberIsMissing() throws Exception {
        // Arrange
        String jsonWithoutAccountNumber = "{\"customerId\":1,\"balance\":1000.0}"; // missing "accountNumber"

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithoutAccountNumber))
                .andExpect(status().isBadRequest()); // Expecting 400 Bad Request

        // Verify that the service method was not called
        verify(bankAccountService, times(0)).createAccount(any(BankAccountDTO.class));
    }

    @Test
    void createAccount_shouldReturnBadRequest_whenBalanceIsMissing() throws Exception {
        // Arrange
        String jsonWithoutBalance = "{\"customerId\":1,\"accountNumber\":\"123456789\"}"; // missing "balance"

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithoutBalance))
                .andExpect(status().isBadRequest()); // Expecting 400 Bad Request

        // Verify that the service method was not called
        verify(bankAccountService, times(0)).createAccount(any(BankAccountDTO.class));
    }

    @Test
    void getAccount_shouldReturnBankAccount() throws Exception {
        // Arrange
        BankAccountDTO bankAccountDTO = new BankAccountDTO(1L, 1L, "123456789", BigDecimal.valueOf(1000.0));
        when(bankAccountService.getAccount(1L)).thenReturn(bankAccountDTO);

        // Act & Assert
        mockMvc.perform(get("/api/bank-accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value("123456789"))
                .andExpect(jsonPath("$.balance").value(1000.0));

        verify(bankAccountService, times(1)).getAccount(1L);
    }

    @Test
    void getAccount_shouldReturnNotFound_whenAccountDoesNotExist() throws Exception {
        // Arrange
        Long nonExistentAccountId = 99L;
        when(bankAccountService.getAccount(nonExistentAccountId))
                .thenThrow(new BankAccountException.BankAccountNotFoundException());

        // Act & Assert
        mockMvc.perform(get("/api/bank-accounts/" + nonExistentAccountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // 404
                .andExpect(content().string("Account not found"));

        verify(bankAccountService, times(1)).getAccount(nonExistentAccountId);
    }

    @Test
    void deleteAccount_shouldReturnNoContent_whenAccountExists() throws Exception {
        // Arrange
        doNothing().when(bankAccountService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/bank-accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Verificamos que devuelve 204 No Content

        verify(bankAccountService, times(1)).delete(1L);
    }


    @Test
    void deleteAccount_shouldReturnNotFound_whenAccountDoesNotExist() throws Exception {
        // Arrange
        doThrow(new BankAccountException.BankAccountNotFoundException()).when(bankAccountService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/bank-accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Verificamos que devuelve 404 Not Found

        verify(bankAccountService, times(1)).delete(1L);
    }

    @Test
    void deposit_shouldReturnBadRequest_whenInvalidTypeIsProvided() throws Exception {
        // Arrange
        String invalidType = "INVALID_TYPE";
        doThrow(new InvalidDepositTypeException(invalidType)).when(bankAccountService).processDeposit(any(DepositDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"accountNumber\":\"123456789\",\"amount\":500.0,\"type\":\"" + invalidType + "\"}"))
                .andExpect(status().isBadRequest());  // 400 Bad Request

        verify(bankAccountService, times(1)).processDeposit(any(DepositDTO.class));
    }

    @Test
    void deposit_shouldReturnBadRequest_whenAmountIsInvalid() throws Exception {
        // Arrange
        doThrow(new InvalidAmountException()).when(bankAccountService).processDeposit(any(DepositDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"accountNumber\":\"123456789\",\"amount\":-500.0,\"type\":\"BRANCH\"}"))
                .andExpect(status().isBadRequest());

        verify(bankAccountService, times(1)).processDeposit(any(DepositDTO.class));
    }

    @Test
    void purchaseCard_shouldReturnOk() throws Exception {
        // Arrange
        doNothing().when(bankAccountService).processCardPurchase(any(PurchaseDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/purchase-card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456789\",\"amount\":200.0,\"type\":\"ONLINE\"}"))
                .andExpect(status().isOk());

        verify(bankAccountService, times(1)).processCardPurchase(any(PurchaseDTO.class));
    }

    @Test
    void purchaseCard_shouldReturnBadRequest_whenInvalidTypeIsProvided() throws Exception {
        // Arrange
        String invalidType = "INVALID_TYPE";

        doThrow(new InvalidPurchaseTypeException(invalidType)).when(bankAccountService).processCardPurchase(any(PurchaseDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/purchase-card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456789\",\"amount\":200.0,\"type\":\"" + invalidType + "\"}"))
                .andExpect(status().isForbidden());  // 403 Forbidden

        verify(bankAccountService, times(1)).processCardPurchase(any(PurchaseDTO.class));
    }

    @Test
    void purchaseCard_shouldReturnForbidden_whenInsufficientFunds() throws Exception {
        // Arrange
        doThrow(new InsufficientFundsException()).when(bankAccountService).processCardPurchase(any(PurchaseDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/purchase-card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456789\",\"amount\":-200.0,\"type\":\"ONLINE\"}"))
                .andExpect(status().isForbidden());  // 403 Forbidden

        verify(bankAccountService, times(1)).processCardPurchase(any(PurchaseDTO.class));
    }

    @Test
    void purchaseCard_shouldReturnBadRequest_whenAmountIsInvalid() throws Exception {
        // Arrange
        doThrow(new InvalidAmountException()).when(bankAccountService).processCardPurchase(any(PurchaseDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/purchase-card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456789\",\"amount\":-200.0,\"type\":\"ONLINE\"}"))
                .andExpect(status().isBadRequest());

        verify(bankAccountService, times(1)).processCardPurchase(any(PurchaseDTO.class));
    }


    @Test
    void withdraw_shouldReturnOk() throws Exception {
        // Arrange
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO(1L, "123456789", BigDecimal.valueOf(300.0));
        doNothing().when(bankAccountService).processWithdrawal(any(WithdrawalDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"accountNumber\":\"123456789\",\"amount\":300.0}"))
                .andExpect(status().isOk());

        verify(bankAccountService, times(1)).processWithdrawal(any(WithdrawalDTO.class));
    }

    @Test
    void withdrawal_shouldReturnBadRequest_whenAmountIsInvalid() throws Exception {
        // Arrange
        doThrow(new InvalidAmountException()).when(bankAccountService).processWithdrawal(any(WithdrawalDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"accountNumber\":\"123456789\",\"amount\":-200.0}"))
                .andExpect(status().isBadRequest());

        verify(bankAccountService, times(1)).processWithdrawal(any(WithdrawalDTO.class));
    }

    @Test
    void withdrawal_shouldReturnNotFound_whenAccountNotFound() throws Exception {
        // Arrange
        doThrow(new BankAccountException.BankAccountNotFoundException()).when(bankAccountService).processWithdrawal(any(WithdrawalDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"accountNumber\":\"123456789\",\"amount\":200.0}"))
                .andExpect(status().isNotFound());

        verify(bankAccountService, times(1)).processWithdrawal(any(WithdrawalDTO.class));
    }

    @Test
    void withdrawal_shouldReturnForbidden_whenInsufficientFunds() throws Exception {
        // Arrange
        doThrow(new InsufficientFundsException()).when(bankAccountService).processWithdrawal(any(WithdrawalDTO.class));

        // Act & Assert
        mockMvc.perform(post("/api/bank-accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"accountNumber\":\"123456789\",\"amount\":200.0}"))
                .andExpect(status().isForbidden());  // 403 Forbidden

        verify(bankAccountService, times(1)).processWithdrawal(any(WithdrawalDTO.class));
    }


}
