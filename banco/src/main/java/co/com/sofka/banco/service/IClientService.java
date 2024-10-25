package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.Client;

import java.util.List;

public interface IClientService {
    List<Client> getAll();
}
