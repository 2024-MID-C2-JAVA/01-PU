package co.com.sofka.sofkau.cuentaflex.services.dtos;

import java.math.BigDecimal;

public record TransactionDoneResponseDto(
        String message,
        BigDecimal balance
) {
}
