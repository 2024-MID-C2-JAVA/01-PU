package com.bank.account.management.service;

import com.bank.account.management.controller.exception.InvalidAmountException;
import com.bank.account.management.controller.exception.InvalidDepositTypeException;
import com.bank.account.management.controller.exception.InvalidPurchaseTypeException;
import com.bank.account.management.model.BankAccount;
import com.bank.account.management.model.Customer;
import com.bank.account.management.model.dto.DepositDTO;
import com.bank.account.management.model.dto.PurchaseDTO;
import com.bank.account.management.model.dto.WithdrawalDTO;
import com.bank.account.management.repository.BankAccountRepository;
import com.bank.account.management.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount mockAccount;
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCustomer = new Customer();
        mockCustomer.setId(1L);

        mockAccount = new BankAccount();
        mockAccount.setAccountNumber("123456789");
        mockAccount.setBalance(BigDecimal.valueOf(1000.0)); // Balance suficiente
        mockAccount.setCustomer(mockCustomer);

        // RESPONSE MOCKS
        when(bankAccountRepository.findByAccountNumber("123456789"))
                .thenReturn(Optional.of(mockAccount));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
    }

    @Test
    void processDeposit_shouldUpdateBalance_whenDepositIsValid() {
        // Arrange
        DepositDTO depositDTO = new DepositDTO(1L, "123456789", BigDecimal.valueOf(500.0), "ATM");

        // Act
        bankAccountService.processDeposit(depositDTO);

        // Assert
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        assertEquals(BigDecimal.valueOf(1498.00).setScale(2, RoundingMode.HALF_UP), mockAccount.getBalance()); // 1000 + 500 - 2 fee
    }

    @Test
    void processDeposit_shouldThrowException_whenDepositDTOIsIncomplete() {
        // Arrange
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setAccountNumber("123456789");
        depositDTO.setAmount(null); // Dejar el monto como null para simular un DTO incompleto
        depositDTO.setCustomerId(1L);
        depositDTO.setType("ATM");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.processDeposit(depositDTO);
        });

        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }


    @Test
    void processDeposit_shouldThrowInvalidAmountException_whenAmountIsInvalid() {
        // Arrange
        DepositDTO depositDTO = new DepositDTO(1L, "123456789", BigDecimal.valueOf(-500.0), "ATM");

        // Act & Assert
        assertThrows(InvalidAmountException.class, () -> bankAccountService.processDeposit(depositDTO));
    }

    @Test
    void processDeposit_shouldThrowInvalidDepositTypeException_whenTypeIsInvalid() {
        // Arrange
        DepositDTO depositDTO = new DepositDTO(1L, "123456789", BigDecimal.valueOf(500.0), "INVALID_TYPE");

        // Act & Assert
        assertThrows(InvalidDepositTypeException.class, () -> bankAccountService.processDeposit(depositDTO));
    }

    @Test
    void processWithdrawal_shouldUpdateBalance_whenWithdrawalIsValid() {
        // Arrange
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO(1L, "123456789", BigDecimal.valueOf(300.0));

        // Act
        bankAccountService.processWithdrawal(withdrawalDTO);

        // Assert
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        assertEquals(BigDecimal.valueOf(699.0).setScale(2, RoundingMode.HALF_UP), mockAccount.getBalance()); // 300 + 1 fee
    }

    @Test
    void processWithdrawal_shouldThrowException_whenWithdrawalDTOIsIncomplete() {
        // Arrange
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
        withdrawalDTO.setAccountNumber("123456789");
        withdrawalDTO.setCustomerId(1L);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.processWithdrawal(withdrawalDTO);
        });

        // Verifica que el repositorio no fue llamado si el DTO es incompleto
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }


    @Test
    void processWithdrawal_shouldThrowInvalidAmountException_whenAmountIsInvalid() {
        // Arrange
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO(1L, "123456789", BigDecimal.valueOf(-300.0));

        // Act & Assert
        assertThrows(InvalidAmountException.class, () -> bankAccountService.processWithdrawal(withdrawalDTO));
    }

    @Test
    void processCardPurchase_shouldUpdateBalance_whenPurchaseIsValid() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setAccountNumber("123456789");
        purchaseDTO.setAmount(BigDecimal.valueOf(200.0));
        purchaseDTO.setPurchaseType("ONLINE");

        // Act
        bankAccountService.processCardPurchase(purchaseDTO);

        // Assert
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        assertEquals(BigDecimal.valueOf(795.0).setScale(2, RoundingMode.HALF_UP), mockAccount.getBalance()); // 200 + 5 fee
    }

    @Test
    void processCardPurchase_shouldThrowException_whenPurchaseDTOIsIncomplete() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setAccountNumber("123456789");
        purchaseDTO.setPurchaseType("ONLINE");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.processCardPurchase(purchaseDTO);
        });

        // Verifica que el repositorio no fue llamado si el DTO es incompleto
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }


    @Test
    void processCardPurchase_shouldThrowInvalidPurchaseTypeException_whenTypeIsInvalid() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setAccountNumber("123456789");
        purchaseDTO.setAmount(BigDecimal.valueOf(200.0));
        purchaseDTO.setPurchaseType("INVALID_TYPE");

        // Act & Assert
        assertThrows(InvalidPurchaseTypeException.class, () -> bankAccountService.processCardPurchase(purchaseDTO));
    }

}
