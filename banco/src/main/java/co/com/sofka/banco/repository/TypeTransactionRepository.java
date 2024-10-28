package co.com.sofka.banco.repository;

import co.com.sofka.banco.model.entity.TypeTransaction;
import co.com.sofka.banco.repository.jpa.JpaTypeTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TypeTransactionRepository implements IGenericFuntion<TypeTransaction> {

    private final JpaTypeTransactionRepository repository;


    @Override
    public TypeTransaction update(TypeTransaction item) {
        return null;
    }

    @Override
    public TypeTransaction save(TypeTransaction item) {
        return null;
    }

    @Override
    public TypeTransaction delete(TypeTransaction item) {
        return null;
    }

    @Override
    public TypeTransaction findById(Long id) {
        return null;
    }

    @Override
    public Long deleteByElementId(Long id) {
        return 0L;
    }

    @Override
    public List<TypeTransaction> getAll() {
        return repository.findAll();
    }
}
