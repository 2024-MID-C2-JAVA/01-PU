package com.bank.account.management.controller;

import com.bank.account.management.model.dto.BankAccountDTO;
import com.bank.account.management.model.dto.DepositDTO;
import com.bank.account.management.model.dto.PurchaseDTO;
import com.bank.account.management.model.dto.WithdrawalDTO;
import com.bank.account.management.service.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    private final IBankAccountService bankAccountService;

    @Autowired
    public BankAccountController(IBankAccountService accountService) {
        this.bankAccountService = accountService;
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> createAccount(@RequestBody BankAccountDTO bankAccountDTO) {
        BankAccountDTO bankAccount = bankAccountService.createAccount(bankAccountDTO);
        return ResponseEntity.ok(bankAccount);
    }

    @GetMapping("/{id}")
    public BankAccountDTO getAccount(@PathVariable Long id) {
        return bankAccountService.getAccount(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteAccount(@PathVariable Long id) {
        bankAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> cardPurchase(@RequestBody DepositDTO depositDTO) {
        bankAccountService.processDeposit(depositDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase-card")
    public ResponseEntity<Void> deposit(@RequestBody PurchaseDTO purchaseDTO) {
        bankAccountService.processCardPurchase(purchaseDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody WithdrawalDTO withdrawalDTO) {
        bankAccountService.processWithdrawal(withdrawalDTO);
        return ResponseEntity.ok().build();

    }
}
