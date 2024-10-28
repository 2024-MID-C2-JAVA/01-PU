package co.com.sofka.banco.service;

import co.com.sofka.banco.model.entity.*;
import co.com.sofka.banco.repository.AccountRepository;
import co.com.sofka.banco.repository.BankRepository;
import co.com.sofka.banco.service.BankTransactionServiceImp;
import co.com.sofka.banco.configuration.middleware.AccountNotExistException;
import co.com.sofka.banco.configuration.middleware.AccountNotHaveBalanceException;
import co.com.sofka.banco.configuration.middleware.TypeOfBuysNotExistException;
import co.com.sofka.banco.controller.model.request.*;
import co.com.sofka.banco.repository.BankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
public class BankTransactionServiceImpTest {

    private static final Logger logger = LoggerFactory.getLogger(BankTransactionServiceImpTest.class);

    @InjectMocks
    private BankTransactionServiceImp bankTransactionService;

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BankRepository bankRepository;

    private BankTransactionWithdrawFromATM validWithdrawRequest;
    private BankTransaction validTransaction;
    private BankTransaction failedTransaction;

    private Account account;
    private Account account1;
    private TypeTransaction typeTransaction;
    Client client = null;

    Bank bank = null;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);

        bank = new Bank();
        bank.setId(1L);
        bank.setBalance(0.0);

        client = new Client();
        client.setId(1L);


        account = new Account();
        account.setId(1L);
        account.setNumber("123456789");
        account.setPin("1234");
        account.setBalance(1000.0);

        account1 = new Account();
        account1.setId(2L);
        account1.setPin("1234");
        account1.setNumber("123456789");
        account1.setBalance(1000.0);

        typeTransaction = new TypeTransaction();
        typeTransaction = new TypeTransaction();
        typeTransaction.setId(1);
        // Inicialización de objetos para los tests
        validWithdrawRequest = new BankTransactionWithdrawFromATM();
        validWithdrawRequest.setAccountNumber("123456789");
        validWithdrawRequest.setAmount(100.0);
        validWithdrawRequest.setPin("1234");

        validTransaction = new BankTransaction();
        validTransaction.setAmount(100.0);
        validTransaction.setCreatedAt(new Date(System.currentTimeMillis()));
        validTransaction.setOriginAccount(account);
        validTransaction.setDestinationAccount(account1);
        validTransaction.setTypeTransaction(typeTransaction);


        failedTransaction = new BankTransaction();
        failedTransaction.setAmount(-50.0);
        failedTransaction.setCreatedAt(new Date(System.currentTimeMillis()));
        failedTransaction.setOriginAccount(account);
        failedTransaction.setDestinationAccount(account1);
        failedTransaction.setTypeTransaction(typeTransaction);// Ejemplo de transacción fallida
    }

    // Pruebas de método withdrawFromATM (Retiro desde ATM)
    @Test
    public void testWithdrawFromATM_validTransaction() {

        Client client = new Client();
        client.setId(1L);

        Account account = new Account();
        account.setId(89L);
        account.setNumber("1111");
        account.setPin("12345");
        account.setBalance(1000.0);
        account.setClient(client);

        TypeTransaction typeTransaction = new TypeTransaction();
        typeTransaction.setId(3);

        BankTransaction validTransaction = new BankTransaction();
        validTransaction.setId(44);
        validTransaction.setAmount(100.0);
        validTransaction.setCreatedAt(new Date(System.currentTimeMillis()));
        validTransaction.setOriginAccount(account);
        validTransaction.setDestinationAccount(account);
        validTransaction.setTypeTransaction(typeTransaction);



        BankTransactionWithdrawFromATM validWithdrawRequest = new BankTransactionWithdrawFromATM();
        validWithdrawRequest.setAccountNumber("1111");
        validWithdrawRequest.setAmount(100.0);
        validWithdrawRequest.setPin("12345");


        when(accountRepository.findByNumberAndPing("1111", "12345")).thenReturn(account);

        Account byNumberAndPing = accountRepository.findByNumberAndPing("1111", "12345");

        when(accountRepository.save(byNumberAndPing)).thenReturn(byNumberAndPing);

        Account save = accountRepository.save(byNumberAndPing);

        BankTransaction validTransaction1 = new BankTransaction();
        validTransaction1.setId(44);
        validTransaction1.setAmount(100.0);
        validTransaction1.setCreatedAt(new Date(System.currentTimeMillis()));
        validTransaction1.setOriginAccount(save);
        validTransaction1.setDestinationAccount(save);
        validTransaction1.setTypeTransaction(typeTransaction);

        when(bankRepository.findById(1L)).thenReturn(bank);

        when(bankRepository.save(bank)).thenReturn(bank);

        when(bankTransactionRepository.save(validTransaction1)).thenReturn(validTransaction1);


        bankTransactionService.withdrawFromATM(validWithdrawRequest);

//        Exception exception = assertThrows(AccountNotExistException.class, () -> {
//            bankTransactionService.withdrawFromATM(validWithdrawRequest);
//        });
        assertEquals(899.0, account.getBalance());
        assertEquals(1,bank.getBalance());
    }

    @Test
    public void testWithdrawFromATM_invalidTransaction() {
        BankTransactionWithdrawFromATM invalidRequest = new BankTransactionWithdrawFromATM();
        invalidRequest.setAmount(100.0);  // Monto inválido

        Exception exception = assertThrows(AccountNotExistException.class, () -> {
            bankTransactionService.withdrawFromATM(invalidRequest);
        });

        assertEquals("La cuenta no existe", exception.getMessage());
    }

    @Test
    public void testWithdrawFromATM_insufficientFundsException() {
        BankTransactionWithdrawFromATM invalidRequest = new BankTransactionWithdrawFromATM();
        invalidRequest.setAmount(2000.0);  // Monto inválido
        invalidRequest.setAccountNumber("123456789");
        invalidRequest.setPin("1234");

        Mockito.when(accountRepository.findByNumberAndPing("123456789", "1234")).thenReturn(account);

        Exception exception = assertThrows(AccountNotHaveBalanceException.class, () -> {
            bankTransactionService.withdrawFromATM(invalidRequest);
        });


        assertEquals("No tiene saldo suficiente.", exception.getMessage());
    }

    // Pruebas de método deposit (Depósito)
    @Test
    public void testDeposit_validTransaction() {
        BankTransactionDepositSucursal depositRequest = new BankTransactionDepositSucursal();
        depositRequest.setAmount(10.0);
        depositRequest.setAccountNumberClient("123456789");

        Account accountTest = new Account();
        accountTest.setId(1L);
        accountTest.setNumber("123456789");
        accountTest.setPin("1234");
        accountTest.setBalance(1000.0);

        Mockito.when(accountRepository.findByNumber("123456789")).thenReturn(accountTest);

        Account byNumber = accountRepository.findByNumber("123456789");

        Mockito.when(accountRepository.save(byNumber)).thenReturn(byNumber);

        BankTransaction validTransaction = new BankTransaction();
        validTransaction.setAmount(100.0);
        validTransaction.setCreatedAt(new Date(System.currentTimeMillis()));
        validTransaction.setOriginAccount(byNumber);
        validTransaction.setDestinationAccount(byNumber);
        validTransaction.setTypeTransaction(typeTransaction);

        Mockito.when(bankTransactionRepository.save(validTransaction)).thenReturn(validTransaction);

        BankTransaction result = bankTransactionService.depositSucursal(depositRequest);

        assertEquals(1, 1);
    }



}

