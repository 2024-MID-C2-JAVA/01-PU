package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAll();
}
