package co.com.sofka.banco.controller.banco;

import co.com.sofka.banco.model.entity.Account;
import co.com.sofka.banco.model.entity.BankTransaction;
import co.com.sofka.banco.model.entity.TypeTransaction;
import co.com.sofka.banco.repository.*;
import co.com.sofka.banco.service.BankTransactionServiceImp;
import co.com.sofka.banco.service.IBankTransactionService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestEntityManager
@WebMvcTest(BankTransactionController.class)
class BankTransactionControllerTest {

//    @MockBean
//    BankTransactionServiceImp service;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private BankRepository bankRepository;

    @MockBean
    private BankTransactionRepository bankTransactionRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private TypeTransactionRepository typeTransactionRepository;

    @Autowired
    MockMvc mockMvc;

    private Account account;
    private Account account1;
    private TypeTransaction typeTransaction;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);

        account = new Account();
        account.setId(1L);

        account1 = new Account();
        account1.setId(2L);

        typeTransaction = new TypeTransaction();
        typeTransaction.setId(1);
    }

    @SneakyThrows
    @Test
    void getAll() {

//        BankTransaction transaction = new BankTransaction();
//        transaction.setOriginAccount(account);
//        transaction.setDestinationAccount(account1);
//        transaction.setAmount(1000.0);
//        transaction.setTypeTransaction(typeTransaction);
//        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//
//        Mockito.when(service.getAll()).thenReturn(List.of(transaction));


        mockMvc.perform(get("/bankTransaction/test"))
                .andExpect(status().isOk());

    }

    @Test
    void bankTransactionWithdrawFromATM() {
    }
}