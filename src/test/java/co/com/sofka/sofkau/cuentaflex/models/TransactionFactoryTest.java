package co.com.sofka.sofkau.cuentaflex.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionFactoryTest {

    @Autowired
    private TransactionFactory transactionFactory;

    @BeforeEach
    void setup() {
        transactionFactory = new TransactionFactory();
    }

    @Test
    void createTransactionShouldReturnTransactionWithCorrectValues() {
        TransactionType type = TransactionType.BRANCH_DEPOSIT;
        BigDecimal amount = new BigDecimal("100.00");
        String description = "Deposit transaction";
        BigDecimal balanceAfterTransaction = new BigDecimal("100.00");

        Transaction transaction = transactionFactory.createTransaction(type, amount, description, balanceAfterTransaction);

        assertEquals(type, transaction.getType());
        assertEquals(amount.setScale(2, RoundingMode.HALF_UP), transaction.getAmount());
        assertEquals(description, transaction.getDescription());
        assertEquals(balanceAfterTransaction.setScale(2, RoundingMode.HALF_UP), transaction.getBalanceAfterTransaction());
        assertNotNull(transaction.getDateTime(), "DateTime should be automatically set at creation");
    }

    @Test
    void createTransactionShouldHandleNegativeAmount() {
        TransactionType type = TransactionType.ATM_WITHDRAWAL;
        BigDecimal amount = new BigDecimal("-50.00");
        String description = "Withdrawal transaction";
        BigDecimal balanceAfterTransaction = new BigDecimal("50.00");

        Transaction transaction = transactionFactory.createTransaction(type, amount, description, balanceAfterTransaction);

        assertEquals(type, transaction.getType());
        assertEquals(amount.setScale(2, RoundingMode.HALF_UP), transaction.getAmount());
        assertEquals(description, transaction.getDescription());
        assertEquals(balanceAfterTransaction.setScale(2, RoundingMode.HALF_UP), transaction.getBalanceAfterTransaction());
        assertNotNull(transaction.getDateTime());
    }
}