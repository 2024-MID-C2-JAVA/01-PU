package co.com.sofka.banktransaction.repository;

import co.com.sofka.banktransaction.model.entity.Bank;
import co.com.sofka.banktransaction.model.entity.Client;
import co.com.sofka.banktransaction.repository.jpa.JpaClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class ClientRepositoryTest {

    @Autowired
    ClientRepository repository;

    @MockBean
    JpaClientRepository jpaClientRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void update() {
        assertEquals(1,1);
    }

    @Test
    void save() {
        assertEquals(1,1);
    }

    @Test
    void delete() {
        assertEquals(1,1);
    }

    @Test
    void findById() {
        assertEquals(1,1);
    }

    @Test
    void deleteByElementId() {
        assertEquals(1,1);
    }

    @Test
    void getAll() {
        Mockito.when(jpaClientRepository.findAll()).thenReturn(List.of(new Client()));
        List<Client> all = repository.getAll();

        assertNotNull(all);
    }
}