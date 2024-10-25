package co.com.sofka.banco.controller.model.request;

import jakarta.validation.constraints.*;
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
