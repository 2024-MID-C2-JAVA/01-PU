package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.services.TransactionsService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AddTransactionDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class PurchasesController {
    private final TransactionsService transactionsService;

    public PurchasesController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/physical-purchases")
    public ResponseEntity<TransactionDoneResponseDto> registerPhysicalPurchase(@RequestBody AddTransactionDto requestBody) {
        return ResponseEntity.ok(this.transactionsService.processPhysicalPurchase(requestBody.amount()));
    }

    @PostMapping("/online-purchases")
    public ResponseEntity<TransactionDoneResponseDto> registerOnlinePurchase(@RequestBody AddTransactionDto requestBody) {
        return ResponseEntity.ok(this.transactionsService.processOnlinePurchase(requestBody.amount()));
    }
}
