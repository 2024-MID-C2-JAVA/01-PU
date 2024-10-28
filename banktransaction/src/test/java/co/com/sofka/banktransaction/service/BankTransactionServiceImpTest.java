package co.com.sofka.banktransaction.service;

import co.com.sofka.banktransaction.controller.dto.request.*;
import co.com.sofka.banktransaction.model.entity.*;
import co.com.sofka.banktransaction.repository.AccountRepository;
import co.com.sofka.banktransaction.repository.BankRepository;
import co.com.sofka.banktransaction.repository.BankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class BankTransactionServiceImpTest {


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
    void setUp() {
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

    @Test
    void getAll() {

        Mockito.when(bankTransactionRepository.getAll()).thenReturn(List.of(validTransaction, failedTransaction));

        assertEquals(2, bankTransactionService.getAll().size());
    }

    @Test
    void withdrawFromATM() {
        Mockito.when(accountRepository.findByNumberAndPing(validWithdrawRequest.getAccountNumber(), validWithdrawRequest.getPin())).thenReturn(account);
        Mockito.when(bankRepository.findById(1L)).thenReturn(bank);
        Mockito.when(bankTransactionRepository.save(ArgumentMatchers.any(BankTransaction.class))).thenReturn(validTransaction);

        BankTransaction bankTransaction = bankTransactionService.withdrawFromATM(validWithdrawRequest);

        assertEquals(validTransaction, bankTransaction);
    }

    @Test
    void buys() {
        Mockito.when(accountRepository.findByNumberAndPing(account.getNumber(), account.getPin())).thenReturn(account);
        Mockito.when(accountRepository.findByNumber(account1.getNumber())).thenReturn(account1);
        Mockito.when(bankTransactionRepository.save(ArgumentMatchers.any(BankTransaction.class))).thenReturn(validTransaction);

        BankTransactionBuys item = new BankTransactionBuys();
        item.setAmount(100.0);
        item.setAccountNumberClient(account.getNumber());
        item.setAccountNumberStore(account1.getNumber());
        item.setPin(account.getPin());
        item.setTypeBuys(1);


        BankTransaction bankTransaction = bankTransactionService.buys(item);

        assertNotNull(bankTransaction);
        //assertEquals(validTransaction, bankTransaction);
    }

    @Test
    void depositSucursal() {

        Mockito.when(accountRepository.findByNumber(account1.getNumber())).thenReturn(account1);
        Mockito.when(bankTransactionRepository.save(ArgumentMatchers.any(BankTransaction.class))).thenReturn(validTransaction);

        BankTransactionDepositSucursal item = new BankTransactionDepositSucursal();
        item.setAmount(100.0);
        item.setAccountNumberClient(account.getNumber());



        BankTransaction bankTransaction = bankTransactionService.depositSucursal(item);

        assertNotNull(bankTransaction);
        //assertEquals(validTransaction, bankTransaction);

    }

    @Test
    void depositCajero() {

        Mockito.when(accountRepository.findByNumberAndPing(account.getNumber(), account.getPin())).thenReturn(account);
        Mockito.when(bankTransactionRepository.save(ArgumentMatchers.any(BankTransaction.class))).thenReturn(validTransaction);
        Mockito.when(bankRepository.findById(1L)).thenReturn(bank);
        Mockito.when(bankRepository.save(ArgumentMatchers.any(Bank.class))).thenReturn(bank);

        BankTransactionDepositCajero item = new BankTransactionDepositCajero();
        item.setAmount(100.0);
        item.setAccountNumberClient(account.getNumber());
        item.setPin(account.getPin());



        BankTransaction bankTransaction = bankTransactionService.depositCajero(item);

        assertNotNull(bankTransaction);
        //assertEquals(validTransaction, bankTransaction);
    }

    @Test
    void depositTrasferencia() {

        Mockito.when(accountRepository.findByNumberAndPing(account.getNumber(), account.getPin())).thenReturn(account);
        Mockito.when(accountRepository.findByNumber(account1.getNumber())).thenReturn(account1);
        Mockito.when(bankTransactionRepository.save(ArgumentMatchers.any(BankTransaction.class))).thenReturn(validTransaction);
        Mockito.when(bankRepository.findById(1L)).thenReturn(bank);
        Mockito.when(bankRepository.save(ArgumentMatchers.any(Bank.class))).thenReturn(bank);

        BankTransactionDepositTransfer item = new BankTransactionDepositTransfer();
        item.setAmount(100.0);
        item.setAccountNumberSender(account.getNumber());
        item.setAccountNumberReceiver(account1.getNumber());
        item.setPin(account.getPin());


        BankTransaction bankTransaction = bankTransactionService.depositTrasferencia(item);

        assertNotNull(bankTransaction);
        //assertEquals(validTransaction, bankTransaction);
    }
}