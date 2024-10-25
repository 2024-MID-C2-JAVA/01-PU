package co.com.sofka.sofkau.cuentaflex.services;

import co.com.sofka.sofkau.cuentaflex.models.Account;
import co.com.sofka.sofkau.cuentaflex.models.TransactionFactory;
import co.com.sofka.sofkau.cuentaflex.models.TransactionType;
import co.com.sofka.sofkau.cuentaflex.repositories.AccountRepository;
import co.com.sofka.sofkau.cuentaflex.repositories.FeesRepository;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import co.com.sofka.sofkau.cuentaflex.services.exceptions.MinimumAmountNotReachedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionsServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private FeesRepository feesRepository;

    @Autowired
    private TransactionFactory transactionFactory;

    private TransactionsService transactionsService;

    private Account mockAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionsService = new TransactionsService(accountRepository, feesRepository, transactionFactory);

        mockAccount = new Account();
        mockAccount.transactAmount(new BigDecimal("100.00"));
        when(accountRepository.getAccount()).thenReturn(mockAccount);
    }

    @Test
    void processBranchDepositShouldApplyTransactionAndSaveAccount() {
        BigDecimal amount = new BigDecimal("200.00");
        BigDecimal fee = new BigDecimal("-10.00");

        when(feesRepository.getFeeValueByTransactionType(TransactionType.BRANCH_DEPOSIT)).thenReturn(fee);
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.BRANCH_DEPOSIT)).thenReturn("Branch Deposit");

        TransactionDoneResponseDto response = transactionsService.processBranchDeposit(amount);

        assertEquals("Transaction was successful!", response.message());
        assertEquals(new BigDecimal("290.00"), response.balance());
        verify(accountRepository, times(1)).saveAccount(mockAccount);
    }

    @Test
    void processAtmDepositShouldFailWhenAmountIsLessThanFee() {
        BigDecimal amount = new BigDecimal("5.00");
        BigDecimal fee = new BigDecimal("-10.00");

        when(feesRepository.getFeeValueByTransactionType(TransactionType.ATM_DEPOSIT)).thenReturn(fee);

        assertThrows(MinimumAmountNotReachedException.class, () -> transactionsService.processAtmDeposit(amount));
        verify(accountRepository, never()).saveAccount(any());
    }

    @Test
    void processAtmWithdrawalShouldApplyFeeAndSaveAccount() {
        BigDecimal amount = new BigDecimal("50.00");
        BigDecimal fee = new BigDecimal("-5.00");

        when(feesRepository.getFeeValueByTransactionType(TransactionType.ATM_WITHDRAWAL)).thenReturn(fee);
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.ATM_WITHDRAWAL)).thenReturn("ATM Withdrawal");
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.FEE)).thenReturn("ATM Fee");

        TransactionDoneResponseDto response = transactionsService.processAtmWithdrawal(amount);

        assertEquals("Transaction was successful!", response.message());
        assertEquals(new BigDecimal("45.00"), response.balance());
        verify(accountRepository, times(1)).saveAccount(mockAccount);
    }

    @Test
    void processOnlinePurchaseWithZeroFee() {
        BigDecimal amount = new BigDecimal("30.00");
        BigDecimal fee = BigDecimal.ZERO;

        when(feesRepository.getFeeValueByTransactionType(TransactionType.ONLINE_PURCHASE)).thenReturn(fee);
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.ONLINE_PURCHASE)).thenReturn("Online Purchase");

        TransactionDoneResponseDto response = transactionsService.processOnlinePurchase(amount);

        assertEquals("Transaction was successful!", response.message());
        assertEquals(new BigDecimal("70.00"), response.balance()); // 100 - 30
        verify(accountRepository, times(1)).saveAccount(mockAccount);
    }

    @Test
    void processExternalDepositShouldIncludeFeeTransaction() {
        BigDecimal amount = new BigDecimal("300.00");
        BigDecimal fee = new BigDecimal("-15.00");

        when(feesRepository.getFeeValueByTransactionType(TransactionType.EXTERNAL_DEPOSIT)).thenReturn(fee);
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.EXTERNAL_DEPOSIT)).thenReturn("External Deposit");
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.FEE)).thenReturn("Transaction Fee");

        TransactionDoneResponseDto response = transactionsService.processExternalDeposit(amount);

        assertEquals("Transaction was successful!", response.message());
        assertEquals(new BigDecimal("385.00"), response.balance());
        verify(accountRepository, times(1)).saveAccount(mockAccount);
    }

    @Test
    void processPhysicalPurchaseWithFeeShouldApplyAndSave() {
        BigDecimal amount = new BigDecimal("80.00");
        BigDecimal fee = new BigDecimal("-8.00");

        when(feesRepository.getFeeValueByTransactionType(TransactionType.PHYSICAL_PURCHASE)).thenReturn(fee);
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.PHYSICAL_PURCHASE)).thenReturn("Physical Purchase");
        when(feesRepository.getFeeDescriptionByTransactionType(TransactionType.FEE)).thenReturn("Transaction Fee");

        TransactionDoneResponseDto response = transactionsService.processPhysicalPurchase(amount);

        assertEquals("Transaction was successful!", response.message());
        assertEquals(new BigDecimal("12.00"), response.balance()); // 100 - 80 - 8
        verify(accountRepository, times(1)).saveAccount(mockAccount);
    }
}
