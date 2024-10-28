package co.com.sofka.banktransaction.repository;


import co.com.sofka.banktransaction.model.entity.Account;

public interface IAccount {

    Account findByNumber(String accountNumber);
    Account findByNumberAndPing(String accountNumber,String pin);
}
