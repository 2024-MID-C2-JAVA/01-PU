package co.com.sofka.banco.controller.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BankTransactionBuys {

    @NotNull(message = "The amount is required")
    @NotBlank(message = "The account number is required")
    private String accountNumberClient;

    @NotNull(message = "The amount is required")
    @NotBlank(message = "The account number is required")
    private String accountNumberStore;

    @NotNull(message = "The amount is required")
    @Min(value = 1, message = "The amount must be greater than zero")
    private Double amount;

    @NotNull(message = "The amount is required")
    @Min(value = 1, message = "The typeBuys must be greater than zero")
    @Max(value = 2, message = "The typeBuys must be greater than zero")
    private int typeBuys;

    @NotNull(message = "The amount is required")
    @NotBlank(message = "The pin is required")
    @Pattern(regexp = "^[0-9]{4,8}$", message = "The pin must be between 4 and 8 characters")
    private String pin;



}
