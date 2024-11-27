package co.com.sofka.sofkau.cuentaflex.repositories.memory;

import co.com.sofka.sofkau.cuentaflex.models.Account;
import co.com.sofka.sofkau.cuentaflex.models.Transaction;
import co.com.sofka.sofkau.cuentaflex.models.TransactionType;
import co.com.sofka.sofkau.cuentaflex.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        this.accountRepository = new InMemoryAccountRepository();
    }

    @Test
    void getAccount_ShouldReturnEmptyAccount_AtFirst() {
        Account account = this.accountRepository.getAccount();

        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), account.getBalance());
        assertEquals(0, account.getTransactions().length);
    }

    @Test
    void saveAccount_ShouldSaveTheProvidedData() {
        BigDecimal balance = new BigDecimal("3.00");
        Account account = this.accountRepository.getAccount();
        account.transactAmount(balance);
        Transaction[] transactions = new Transaction[] {
                new Transaction(
                        TransactionType.ATM_WITHDRAWAL,
                        new BigDecimal("1.44"),
                        "Test description 1",
                        new BigDecimal("1.44")
                ),
                new Transaction(
                        TransactionType.ATM_WITHDRAWAL,
                        new BigDecimal("3.00"),
                        "Test description 2",
                        new BigDecimal("3.44")
                )
        };
        for(Transaction transaction : transactions) {
            account.addTransaction(transaction);
        }

        this.accountRepository.saveAccount(account);
        Transaction[] actualTransactions = account.getTransactions();

        assertEquals(balance, account.getBalance());
        assertEquals(transactions.length, actualTransactions.length);
        for(int i = 0; i < transactions.length; i++) {
            assertEquals(transactions[transactions.length - i - 1].getType(), actualTransactions[i].getType());
            assertEquals(transactions[transactions.length - i - 1].getAmount(), actualTransactions[i].getAmount());
            assertEquals(transactions[transactions.length - i - 1].getDescription(), actualTransactions[i].getDescription());
            assertEquals(transactions[transactions.length - i - 1].getBalanceAfterTransaction(), actualTransactions[i].getBalanceAfterTransaction());
            assertEquals(transactions[transactions.length - i - 1].getDateTime(), actualTransactions[i].getDateTime());
        }
    }

}