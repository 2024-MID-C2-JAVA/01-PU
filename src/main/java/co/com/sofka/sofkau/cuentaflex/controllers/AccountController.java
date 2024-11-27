package co.com.sofka.sofkau.cuentaflex.controllers;

import co.com.sofka.sofkau.cuentaflex.services.AccountService;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AccountDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<AccountDto> getAccount() {
        AccountDto account = this.accountService.getAccount();

        if (account == null) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }

        return ResponseEntity.ok(account);
    }
}
