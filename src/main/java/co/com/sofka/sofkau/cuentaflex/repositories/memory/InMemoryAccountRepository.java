package co.com.sofka.sofkau.cuentaflex.repositories.memory;

import co.com.sofka.sofkau.cuentaflex.models.Account;
import co.com.sofka.sofkau.cuentaflex.repositories.AccountRepository;

public class InMemoryAccountRepository implements AccountRepository {
    private static Account globalAccount = new Account();

    @Override
    public Account getAccount() {
        return new Account(globalAccount);
    }

    @Override
    public void saveAccount(Account account) {
        globalAccount = account;
    }
}
