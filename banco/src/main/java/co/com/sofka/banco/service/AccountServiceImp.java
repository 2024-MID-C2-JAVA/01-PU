package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.Account;
import co.com.sofka.banco.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImp implements IAccountService {

    private final AccountRepository repository;

    @Override
    public List<Account> getAll() {
        return repository.getAll();
    }
}
