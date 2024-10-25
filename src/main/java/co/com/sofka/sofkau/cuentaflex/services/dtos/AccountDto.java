package co.com.sofka.sofkau.cuentaflex.services.dtos;

import java.math.BigDecimal;

public record AccountDto(
        BigDecimal balance,
        TransactionDto[] transactions
) {
}
