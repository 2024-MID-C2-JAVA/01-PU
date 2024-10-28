package co.com.sofka.banco.repository;

import co.com.sofka.banco.model.entity.Client;
import co.com.sofka.banco.repository.jpa.JpaClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ClientRepository implements IGenericFuntion<Client> {

    private final JpaClientRepository repository;


    @Override
    public Client update(Client item) {
        return null;
    }

    @Override
    public Client save(Client item) {
        return null;
    }

    @Override
    public Client delete(Client item) {
        return null;
    }

    @Override
    public Client findById(Long id) {
        return null;
    }

    @Override
    public Long deleteByElementId(Long id) {
        return 0L;
    }

    @Override
    public List<Client> getAll() {
        return repository.findAll();
    }
}
