package co.com.sofka.banktransaction.repository;


import co.com.sofka.banktransaction.model.entity.Bank;
import co.com.sofka.banktransaction.repository.jpa.JpaBankRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BankRepository implements IGenericFuntion<Bank> {

    private final JpaBankRepository repository;


    @Override
    public Bank update(Bank item) {
        return repository.save(item);
    }

    @Override
    public Bank save(Bank item) {
        return repository.save(item);
    }

    @Override
    public Bank delete(Bank item) {
        item.setIsActive(false);
        repository.save(item);
        return item;
    }

    @Override
    public Bank findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Long deleteByElementId(Long id) {
        repository.deleteById(id);
        return id;
    }

    @Override
    public List<Bank> getAll() {
        return repository.findAll();
    }
}
