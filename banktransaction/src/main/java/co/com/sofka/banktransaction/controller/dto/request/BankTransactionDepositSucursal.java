package co.com.sofka.banktransaction.controller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BankTransactionDepositSucursal {

    @NotNull(message = "The amount is required")
    @NotBlank(message = "The account number is required")
    private String accountNumberClient;

    @NotNull(message = "The amount is required")
    @Min(value = 1, message = "The amount must be greater than zero")
    private Double amount;
}
