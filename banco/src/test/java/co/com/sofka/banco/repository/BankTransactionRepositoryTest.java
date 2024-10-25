package co.com.sofka.banco.repository;


import co.com.sofka.banco.model.entity.BankTransaction;
import co.com.sofka.banco.repository.jpa.JpaBankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BankTransactionRepositoryTest {

    @Mock
    private JpaBankTransactionRepository jpaBankTransactionRepository;

    @InjectMocks
    private BankTransactionRepository bankTransactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        BankTransaction transaction1 = new BankTransaction();
        transaction1.setId(1);
        transaction1.setAmount(100.00);

        BankTransaction transaction2 = new BankTransaction();
        transaction2.setId(2);
        transaction2.setAmount(200.00);

        when(jpaBankTransactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));

        List<BankTransaction> result = bankTransactionRepository.getAll();

        assertEquals(2, result.size());
        assertEquals(100.00, result.get(0).getAmount());
        assertEquals(200.00, result.get(1).getAmount());
    }
}