package co.com.sofka.banktransaction.repository;

import co.com.sofka.banktransaction.model.entity.Account;
import co.com.sofka.banktransaction.model.entity.BankTransaction;
import co.com.sofka.banktransaction.model.entity.TypeTransaction;
import co.com.sofka.banktransaction.repository.jpa.JpaBankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
public class BankTransactionRepositoryTest {

    @Autowired
    BankTransactionRepository repository;

    @MockBean
    JpaBankTransactionRepository jpaBankTransactionRepository;

    private Account account;
    private Account account1;
    private TypeTransaction typeTransaction;


    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(1L);

        account1 = new Account();
        account1.setId(2L);

        typeTransaction = new TypeTransaction();
        typeTransaction.setId(1);

    }

    @Test
    public void testSaveValidTransaction() {
        BankTransaction transaction = new BankTransaction();
        transaction.setOriginAccount(account);
        transaction.setDestinationAccount(account1);
        transaction.setAmount(1000.0);
        transaction.setTypeTransaction(typeTransaction);
        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Mockito.when(jpaBankTransactionRepository.save(Mockito.any(BankTransaction.class))).thenReturn(transaction);
        BankTransaction savedTransaction = repository.save(transaction);

        assertNotNull(savedTransaction);

    }


    @Test
    public void testUpdateValidTransaction() {
        BankTransaction transaction = new BankTransaction();
        transaction.setOriginAccount(account);
        transaction.setDestinationAccount(account1);
        transaction.setAmount(1000.0);
        transaction.setTypeTransaction(typeTransaction);
        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));


        Mockito.when(jpaBankTransactionRepository.save(Mockito.any(BankTransaction.class))).thenReturn(transaction);

        BankTransaction savedTransaction = repository.save(transaction);
        savedTransaction.setAmount(2000.0);

        BankTransaction updatedTransaction = repository.update(savedTransaction);

        assertNotNull(updatedTransaction);
        assertNotNull(updatedTransaction);
        assertEquals(2000.0, updatedTransaction.getAmount());

    }

    @Test
    public void testFindById() {
        BankTransaction transaction = new BankTransaction();
        transaction.setId(1);
        transaction.setOriginAccount(account);
        transaction.setDestinationAccount(account1);
        transaction.setAmount(1000.0);
        transaction.setTypeTransaction(typeTransaction);
        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Mockito.when(jpaBankTransactionRepository.save(Mockito.any(BankTransaction.class))).thenReturn(transaction);


        BankTransaction savedTransaction = repository.save(transaction);

        Mockito.when(jpaBankTransactionRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(transaction));


        BankTransaction foundTransaction = repository.findById(Long.valueOf(savedTransaction.getId()));


        assertNotNull(foundTransaction);
        assertNotNull(foundTransaction);
        assertEquals(savedTransaction.getId(), foundTransaction.getId());

    }

    @Test
    public void testDeleteByElementId() {
        BankTransaction transaction = new BankTransaction();
        transaction.setId(1);
        transaction.setOriginAccount(account);
        transaction.setDestinationAccount(account1);
        transaction.setAmount(1000.0);
        transaction.setTypeTransaction(typeTransaction);
        Mockito.when(jpaBankTransactionRepository.save(Mockito.any(BankTransaction.class))).thenReturn(transaction);


        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        BankTransaction savedTransaction = repository.save(transaction);

        Long deletedId = repository.deleteByElementId(Long.valueOf(savedTransaction.getId()));

        //Mostrar porque es diferente Lon a Long Value
        assertEquals(savedTransaction.getId().longValue(), deletedId.longValue());
    }



    @Test
    public void testGetAll() {
        BankTransaction transaction = new BankTransaction();
        transaction.setOriginAccount(account);
        transaction.setDestinationAccount(account1);
        transaction.setAmount(1000.0);
        transaction.setTypeTransaction(typeTransaction);
        Mockito.when(jpaBankTransactionRepository.findAll()).thenReturn(List.of(transaction));

        List<BankTransaction> transactions = repository.getAll();
        assertNotNull(transactions);
        assertTrue(transactions.size() > 0);
    }
}


