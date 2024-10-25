package co.com.sofka.sofkau.cuentaflex.models;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionFactory {
    public Transaction createTransaction(TransactionType type, BigDecimal amount, String description, BigDecimal balanceAfterTransaction) {
        return new Transaction(type, amount, description, balanceAfterTransaction);
    }
}
