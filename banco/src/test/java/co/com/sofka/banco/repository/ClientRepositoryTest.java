package co.com.sofka.banco.repository;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


import co.com.sofka.banco.model.entity.Client;
import co.com.sofka.banco.repository.jpa.JpaClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientRepositoryTest {

    @Mock
    private JpaClientRepository jpaClientRepository;

    @InjectMocks
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Client 1");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Client 2");

        List<Client> clients = Arrays.asList(client1, client2);

        when(jpaClientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientRepository.getAll();

        assertEquals(2, result.size());
        assertEquals("Client 1", result.get(0).getName());
        assertEquals("Client 2", result.get(1).getName());
    }
}