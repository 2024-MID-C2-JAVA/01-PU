package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.models.exceptions.MinimumAmountNotReachedException;
import co.com.sofka.sofkau.cuentaflex.services.exceptions.InsufficientBalanceException;
import co.com.sofka.sofkau.cuentaflex.services.TransactionsService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AddTransactionDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class DepositsController {
    private final TransactionsService transactionsService;

    public DepositsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/branch-deposits")
    public ResponseEntity<TransactionDoneResponseDto> registerBranchDeposit(AddTransactionDto requestBody) {
        return ResponseEntity.ok(this.transactionsService.processBranchDeposit(requestBody.amount()));
    }

    @PostMapping("/atm-deposits")
    public ResponseEntity<TransactionDoneResponseDto> registerAtmDeposit(AddTransactionDto requestBody) {
        return ResponseEntity.ok(this.transactionsService.processAtmDeposit(requestBody.amount()));
    }

    @PostMapping("/external-deposits")
    public ResponseEntity<TransactionDoneResponseDto> registerExternalDeposit(AddTransactionDto requestBody) {
        return ResponseEntity.ok(this.transactionsService.processExternalDeposit(requestBody.amount()));
    }

    @ExceptionHandler(MinimumAmountNotReachedException.class)
    public ResponseEntity<String> handleMinimumAmountNotReachedException(MinimumAmountNotReachedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
