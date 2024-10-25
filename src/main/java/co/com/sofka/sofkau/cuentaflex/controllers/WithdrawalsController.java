package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.services.TransactionsService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AddTransactionDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class WithdrawalsController {
    private final TransactionsService transactionsService;

    public WithdrawalsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/atm-withdrawals")
    public ResponseEntity<TransactionDoneResponseDto> registerAtmWithdrawal(AddTransactionDto requestBody) {
        return ResponseEntity.ok(this.transactionsService.processAtmWithdrawal(requestBody.amount()));
    }
}
