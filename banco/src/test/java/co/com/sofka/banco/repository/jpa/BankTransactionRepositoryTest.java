package co.com.sofka.banco.repository.jpa;


import co.com.sofka.banco.model.entity.Account;
import co.com.sofka.banco.model.entity.BankTransaction;
import co.com.sofka.banco.model.entity.TypeTransaction;
import co.com.sofka.banco.repository.BankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
public class BankTransactionRepositoryTest {

        @Autowired
        private BankTransactionRepository repository;

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
                BankTransaction savedTransaction = repository.save(transaction);

                assertNotNull(savedTransaction);

                assertNotNull(savedTransaction);
                assertNotNull(savedTransaction.getId());

        }

        @Test
        public void testSaveInvalidTransaction() {

                BankTransaction transaction = new BankTransaction();
                transaction.setAmount(1000.0);
                transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                assertThrows(DataIntegrityViolationException.class, () -> {
                    repository.save(transaction);
                });

        }

        @Test
        public void testUpdateValidTransaction() {
                BankTransaction transaction = new BankTransaction();
                transaction.setOriginAccount(account);
                transaction.setDestinationAccount(account1);
                transaction.setAmount(1000.0);
                transaction.setTypeTransaction(typeTransaction);
                transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
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
                transaction.setOriginAccount(account);
                transaction.setDestinationAccount(account1);
                transaction.setAmount(1000.0);
                transaction.setTypeTransaction(typeTransaction);
                transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));


                BankTransaction savedTransaction = repository.save(transaction);


                BankTransaction foundTransaction = repository.findById(Long.valueOf(savedTransaction.getId()));


                assertNotNull(foundTransaction);
                assertNotNull(foundTransaction);
                assertEquals(savedTransaction.getId(), foundTransaction.getId());

        }

        @Test
        public void testDeleteByElementId() {
                BankTransaction transaction = new BankTransaction();
                transaction.setOriginAccount(account);
                transaction.setDestinationAccount(account1);
                transaction.setAmount(1000.0);
                transaction.setTypeTransaction(typeTransaction);

                transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                BankTransaction savedTransaction = repository.save(transaction);

                Long deletedId = repository.deleteByElementId(Long.valueOf(savedTransaction.getId()));

                //Mostrar porque es diferente Lon a Long Value
                assertEquals(savedTransaction.getId().longValue(), deletedId.longValue());
            }



            @Test
            public void testGetAll() {
                List<BankTransaction> transactions = repository.getAll();
                assertNotNull(transactions);
                assertTrue(transactions.size() > 0);
            }
        }


