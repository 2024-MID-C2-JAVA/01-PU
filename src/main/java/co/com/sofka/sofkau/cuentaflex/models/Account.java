package co.com.sofka.sofkau.cuentaflex.models;

import co.com.sofka.sofkau.cuentaflex.models.exceptions.InsufficientBalanceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Account {
    private BigDecimal balance;
    private final List<Transaction> transactions;

    public Account() {
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.transactions = new LinkedList<>();
    }

    public Account(Account other) {
        this.balance = other.balance;
        this.transactions = new LinkedList<>(other.transactions);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Transaction[] getTransactions() {
        return transactions.toArray(Transaction[]::new);
    }

    public void transactAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 && amount.abs().compareTo(balance) > 0) {
            throw new InsufficientBalanceException("Insufficient balance in the account to process the transaction");
        }

        this.balance = balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.addFirst(transaction);
    }
}
