package co.com.sofka.banktransaction.controller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankTransactionWithdrawFromATM {

    @NotNull(message = "The amount is required")
    @NotBlank(message = "The account number is required")
    private String accountNumber;

    @NotNull(message = "The amount is required")
    @Min(value = 1, message = "The amount must be greater than zero")
    private Double amount;

    @NotNull(message = "The amount is required")
    @NotBlank(message = "The pin is required")
    @Pattern(regexp = "^[0-9]{4,8}$", message = "The pin must be between 4 and 8 characters")
    private String pin;



}
