package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.Client;
import co.com.sofka.banco.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImp implements IClientService {

    private final ClientRepository repository;

    @Override
    public List<Client> getAll() {
        return repository.getAll();
    }
}
