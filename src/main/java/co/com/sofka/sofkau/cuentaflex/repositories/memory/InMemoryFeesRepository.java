package co.com.sofka.sofkau.cuentaflex.repositories.memory;

import co.com.sofka.sofkau.cuentaflex.models.TransactionType;
import co.com.sofka.sofkau.cuentaflex.repositories.FeesRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InMemoryFeesRepository implements FeesRepository {
    private final static HashMap<TransactionType, BigDecimal> feesByTransactionType = new HashMap<>(
            Map.of(
                    TransactionType.FEE, BigDecimal.ZERO,
                    TransactionType.BRANCH_DEPOSIT, BigDecimal.ZERO,
                    TransactionType.ATM_DEPOSIT, new BigDecimal("-2.00"),
                    TransactionType.EXTERNAL_DEPOSIT, new BigDecimal("-1.50"),
                    TransactionType.PHYSICAL_PURCHASE, BigDecimal.ZERO,
                    TransactionType.ONLINE_PURCHASE, new BigDecimal("-5.00"),
                    TransactionType.ATM_WITHDRAWAL, new BigDecimal("-1.00")
            )
    );

    private final static HashMap<TransactionType, String> descriptionsByTransactionType = new HashMap<>(
            Map.of(
                    TransactionType.FEE, "Service fee",
                    TransactionType.BRANCH_DEPOSIT, "Deposit from bank branch",
                    TransactionType.ATM_DEPOSIT, "Deposit from ATM",
                    TransactionType.EXTERNAL_DEPOSIT, "Deposit from external account",
                    TransactionType.PHYSICAL_PURCHASE, "Purchase in physical store",
                    TransactionType.ONLINE_PURCHASE, "Purchase in online store",
                    TransactionType.ATM_WITHDRAWAL, "ATM Withdrawal"
            )
    );

    @Override
    public BigDecimal getFeeValueByTransactionType(TransactionType transactionType) {
        return feesByTransactionType.get(transactionType);
    }

    @Override
    public String getFeeDescriptionByTransactionType(TransactionType transactionType) {
        return descriptionsByTransactionType.get(transactionType);
    }
}
