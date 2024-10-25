package co.com.sofka.sofkau.cuentaflex.repositories;

import co.com.sofka.sofkau.cuentaflex.models.Account;

public interface AccountRepository {
    public Account getAccount();
    public void saveAccount(Account account);
}
