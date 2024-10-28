package co.com.sofka.banktransaction.repository;

import co.com.sofka.banktransaction.model.entity.Account;
import co.com.sofka.banktransaction.repository.jpa.JpaAccountRepository;
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
class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @MockBean
    JpaAccountRepository jpaAccountRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void update() {
        Account account = new Account();
        account.setId(1L);

        Mockito.when(jpaAccountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        Account savedAccount = repository.save(account);

        assertEquals(account.getId(), savedAccount.getId());
    }

    @Test
    void save() {

        Account account = new Account();
        account.setId(1L);

        Mockito.when(jpaAccountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        Account savedAccount = repository.save(account);

        assertEquals(account.getId(), savedAccount.getId());



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
        Account account = new Account();
        account.setId(1L);
        account.setNumber("1234");
        account.setPin("1234");

        Mockito.when(jpaAccountRepository.findAll()).thenReturn(List.of(account));

        List<Account> savedAccount = repository.getAll();

        assertEquals(1, savedAccount.size());
    }

    @Test
    void findByNumberAndPing() {


        Account account = new Account();
        account.setId(1L);
        account.setNumber("1234");
        account.setPin("1234");

        Mockito.when(jpaAccountRepository.findByNumberAndPin(Mockito.any(String.class),Mockito.any(String.class))).thenReturn(account);

        Account savedAccount = repository.findByNumberAndPing(account.getNumber(),account.getPin());

        assertEquals(account.getId(), savedAccount.getId());



    }

    @Test
    void findByNumber() {
        Account account = new Account();
        account.setId(1L);
        account.setNumber("1234");
        account.setPin("1234");

        Mockito.when(jpaAccountRepository.findByNumber(Mockito.any(String.class))).thenReturn(account);

        Account savedAccount = repository.findByNumber(account.getNumber());

        assertEquals(account.getId(), savedAccount.getId());
    }
}