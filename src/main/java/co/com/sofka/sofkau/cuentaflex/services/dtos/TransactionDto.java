package co.com.sofka.sofkau.cuentaflex.services.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        BigDecimal amount,
        String description,
        BigDecimal balanceAfterTransaction,
        LocalDateTime dateTime
) {
}
