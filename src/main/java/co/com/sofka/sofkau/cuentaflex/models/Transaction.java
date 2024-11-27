package co.com.sofka.sofkau.cuentaflex.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final String description;
    private final BigDecimal balanceAfterTransaction;
    private final LocalDateTime dateTime;

    public Transaction(
            TransactionType type,
            BigDecimal amount,
            String description,
            BigDecimal balanceAfterTransaction
    ) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.dateTime = LocalDateTime.now();
    }

    public TransactionType getType() {
        return this.type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return this.balanceAfterTransaction;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
