package co.com.sofka.banco.repository;

import co.com.sofka.banco.model.entity.Account;

public interface IAccount {

    Account findByNumber(String accountNumber);
    Account findByNumberAndPing(String accountNumber,String pin);
}
