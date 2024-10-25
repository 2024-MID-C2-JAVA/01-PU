package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.TypeTransaction;
import co.com.sofka.banco.repository.TypeTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeTransactionServiceImp implements ITypeTransactionService {

    private final TypeTransactionRepository repository;

    @Override
    public List<TypeTransaction> getAll() {
        return repository.getAll();
    }
}
