package co.com.sofka.banco.repository;

import co.com.sofka.banco.model.entity.TypeTransaction;
import co.com.sofka.banco.repository.jpa.JpaTypeTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TypeTransactionRepositoryTest {

    @Mock
    private JpaTypeTransactionRepository jpaTypeTransactionRepository;

    @InjectMocks
    private TypeTransactionRepository typeTransactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        TypeTransaction typeTransaction1 = new TypeTransaction();
        typeTransaction1.setId(1);
        typeTransaction1.setName("Type 1");



        TypeTransaction typeTransaction2 = new TypeTransaction();
        typeTransaction2.setId(2);
        typeTransaction2.setName("Type 2");
        when(jpaTypeTransactionRepository.findAll()).thenReturn(List.of(typeTransaction1, typeTransaction2));

        List<TypeTransaction> result = typeTransactionRepository.getAll();

        assertEquals(2, result.size());
        assertEquals("Type 1", result.get(0).getName());
        assertEquals("Type 2", result.get(1).getName());
    }
}