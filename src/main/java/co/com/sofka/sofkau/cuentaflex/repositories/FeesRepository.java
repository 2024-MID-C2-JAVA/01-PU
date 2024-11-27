package co.com.sofka.sofkau.cuentaflex.repositories;

import co.com.sofka.sofkau.cuentaflex.models.TransactionType;

import java.math.BigDecimal;

public interface FeesRepository {
    public BigDecimal getFeeValueByTransactionType(TransactionType transactionType);
    public String getFeeDescriptionByTransactionType(TransactionType transactionType);
}
