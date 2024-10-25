package co.com.sofka.sofkau.cuentaflex.services;

import co.com.sofka.sofkau.cuentaflex.models.Account;
import co.com.sofka.sofkau.cuentaflex.models.Transaction;
import co.com.sofka.sofkau.cuentaflex.models.TransactionType;
import co.com.sofka.sofkau.cuentaflex.repositories.AccountRepository;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AccountDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountShouldReturnAccountDtoWhenAccountExists() {
        Account mockAccount = new Account();
        mockAccount.transactAmount(new BigDecimal("100.00"));
        Transaction transaction = new Transaction(
                TransactionType.BRANCH_DEPOSIT,
                new BigDecimal("100.00"),
                "Test deposit",
                new BigDecimal("100.00")
        );
        mockAccount.addTransaction(transaction);
        when(accountRepository.getAccount()).thenReturn(mockAccount);

        AccountDto accountDto = accountService.getAccount();

        assertNotNull(accountDto, "Expected AccountDto to be returned when account exists");
        assertEquals(mockAccount.getBalance(), accountDto.balance());
        assertEquals(1, accountDto.transactions().length);

        TransactionDto transactionDto = accountDto.transactions()[0];
        assertEquals(transaction.getAmount(), transactionDto.amount());
        assertEquals(transaction.getDescription(), transactionDto.description());
        assertEquals(transaction.getBalanceAfterTransaction(), transactionDto.balanceAfterTransaction());
        assertEquals(transaction.getDateTime(), transactionDto.dateTime());
    }

    @Test
    void getAccount_ShouldReturnNull_IfAccountDoesNotExist() {
        when(accountRepository.getAccount()).thenReturn(null);

        AccountDto accountDto = accountService.getAccount();

        assertNull(accountDto, "Expected null to be returned when no account exists");
    }
}