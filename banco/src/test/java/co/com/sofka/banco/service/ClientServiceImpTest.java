package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.Client;
import co.com.sofka.banco.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImpTest {

    @Mock
    private ClientRepository repository;

    Client client = null;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);
    }

    @Test
    void getAll() {
        Mockito.when(repository.getAll()).thenReturn(List.of(client));

        List<Client> all = repository.getAll();

        assertNotNull(all);
        assertEquals(1, all.size());
    }
}