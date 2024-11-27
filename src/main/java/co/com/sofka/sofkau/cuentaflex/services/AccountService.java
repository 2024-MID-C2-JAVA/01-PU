package co.com.sofka.sofkau.cuentaflex.services;

import co.com.sofka.sofkau.cuentaflex.models.Account;
import co.com.sofka.sofkau.cuentaflex.repositories.AccountRepository;
import co.com.sofka.sofkau.cuentaflex.services.dtos.AccountDto;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getAccount() {
        Account account = this.accountRepository.getAccount();

        if (account == null) {
            return null;
        }

        return new AccountDto(
                account.getBalance(),
                Arrays.stream(account.getTransactions()).map(
                        x -> new TransactionDto(
                                x.getAmount(),
                                x.getDescription(),
                                x.getBalanceAfterTransaction(),
                                x.getDateTime()
                        )
                ).toArray(TransactionDto[]::new)
        );
    }
}
