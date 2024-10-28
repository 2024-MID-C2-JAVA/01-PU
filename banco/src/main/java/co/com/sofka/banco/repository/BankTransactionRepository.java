package co.com.sofka.banco.repository;

import co.com.sofka.banco.model.entity.BankTransaction;
import co.com.sofka.banco.repository.jpa.JpaBankTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BankTransactionRepository implements IGenericFuntion<BankTransaction> {

    private final JpaBankTransactionRepository repository;


    @Override
    public BankTransaction update(BankTransaction item) {
        return repository.save(item);
    }

    @Override
    public BankTransaction save(BankTransaction item) {
        return repository.save(item);
    }

    @Override
    public BankTransaction delete(BankTransaction item) {
        return null;
    }

    @Override
    public BankTransaction findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Long deleteByElementId(Long id) {
        return id;
    }

    @Override
    public List<BankTransaction> getAll() {
        return repository.findAll();
    }
}
