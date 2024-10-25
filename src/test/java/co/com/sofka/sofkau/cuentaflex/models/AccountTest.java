package co.com.sofka.sofkau.cuentaflex.models;

import co.com.sofka.sofkau.cuentaflex.models.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void accountStartsWithBalanceZero_WithDefaultConstructor() {
        Account account = new Account();

        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), account.getBalance());
    }

    @Test
    void transactAmount_BalanceIncrease_IfPositiveAmountIsPassed1() {
        Account account = new Account();

        account.transactAmount(new BigDecimal("2.30"));

        assertEquals(new BigDecimal("2.30"), account.getBalance());
    }

    @Test
    void transactAmount_BalanceIncrease_IfPositiveAmountIsPassed2() {
        Account account = new Account();

        account.transactAmount(new BigDecimal("2.30"));
        account.transactAmount(new BigDecimal("1.00"));
        account.transactAmount(new BigDecimal("0.57"));

        assertEquals(new BigDecimal("3.87"), account.getBalance());
    }

    @Test
    void transactAmount_BalanceDecrease_IfNegativeAmountIsPassed() {
        Account account = new Account();
        account.transactAmount(new BigDecimal("3.87"));

        account.transactAmount(new BigDecimal("-1.25"));

        assertEquals(new BigDecimal("2.62"), account.getBalance());
    }

    @Test
    void transactAmount_BalanceShouldBeZero_IfNegativeAmountAbsIsEqualsToBalance() {
        Account account = new Account();
        account.transactAmount(new BigDecimal("3.87"));

        account.transactAmount(new BigDecimal("-3.87"));

        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), account.getBalance());
    }

    @Test
    void transactAmount_ThrowsInsufficientBalanceException_IfNegativeAmountAbsIsGreaterThanBalance() {
        Account account = new Account();
        account.transactAmount(new BigDecimal("7.82"));

        assertThrows(InsufficientBalanceException.class, () -> {
            account.transactAmount(new BigDecimal("-8.00"));
        });
    }
}