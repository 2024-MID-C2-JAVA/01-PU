package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.TypeTransaction;

import java.util.List;

public interface ITypeTransactionService {
    List<TypeTransaction> getAll();
}
