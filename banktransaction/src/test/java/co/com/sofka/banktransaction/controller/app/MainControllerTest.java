package co.com.sofka.banktransaction.controller.app;

import co.com.sofka.banktransaction.controller.dto.Response;
import co.com.sofka.banktransaction.controller.dto.request.*;
import co.com.sofka.banktransaction.model.entity.*;
import co.com.sofka.banktransaction.service.IBankTransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MainController.class)
@ContextConfiguration(classes = MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBankTransactionService service;


    private BankTransactionWithdrawFromATM validWithdrawRequest;
    private BankTransaction validTransaction;
    private BankTransaction failedTransaction;

    private Account account;
    private Account account1;
    private TypeTransaction typeTransaction;
    Client client = null;

    Bank bank = null;


    ObjectMapper objectMapper = new ObjectMapper();


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
    void tets() throws Exception {
        mockMvc.perform(get("/bankTransaction/test"))
                .andExpect(status().isOk());
    }



    @Test
    void getAll() throws Exception {

        Mockito.when(service.getAll()).thenReturn(List.of(validTransaction,failedTransaction));

        mockMvc.perform(get("/bankTransaction/all"))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).getAll();

    }

    @Test
    void bankTransactionWithdrawFromATM() throws Exception {

        Response<BankTransaction> transaccionBancariaGuardada = Response.<BankTransaction>builder().bodyOut(validTransaction)
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build();


        BankTransactionWithdrawFromATM bankTransaction = new BankTransactionWithdrawFromATM();


        Mockito.when(service.withdrawFromATM(bankTransaction)).thenReturn(validTransaction);

        mockMvc.perform(post("/bankTransaction/retitarCajero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bodyOut.amount").value(100.0));

        Mockito.verify(service, Mockito.times(1)).withdrawFromATM(bankTransaction);
    }

    @Test
    void bankTransactionBuys() throws Exception {
        Response<BankTransaction> transaccionBancariaGuardada = Response.<BankTransaction>builder().bodyOut(validTransaction)
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build();


        BankTransactionBuys bankTransaction = new BankTransactionBuys();


        Mockito.when(service.buys(bankTransaction)).thenReturn(validTransaction);

        mockMvc.perform(post("/bankTransaction/buys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bodyOut.amount").value(100.0));

        Mockito.verify(service, Mockito.times(1)).buys(bankTransaction);
    }

    @Test
    void bankTransactionDeposit() throws Exception {
        Response<BankTransaction> transaccionBancariaGuardada = Response.<BankTransaction>builder().bodyOut(validTransaction)
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build();


        BankTransactionDepositSucursal bankTransaction = new BankTransactionDepositSucursal();


        Mockito.when(service.depositSucursal(bankTransaction)).thenReturn(validTransaction);

        mockMvc.perform(post("/bankTransaction/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bodyOut.amount").value(100.0));

        Mockito.verify(service, Mockito.times(1)).depositSucursal(bankTransaction);
    }

    @Test
    void bankTransactionDepositCajero() throws Exception {
        Response<BankTransaction> transaccionBancariaGuardada = Response.<BankTransaction>builder().bodyOut(validTransaction)
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build();


        BankTransactionDepositCajero bankTransaction = new BankTransactionDepositCajero();


        Mockito.when(service.depositCajero(bankTransaction)).thenReturn(validTransaction);

        mockMvc.perform(post("/bankTransaction/depositCajero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bodyOut.amount").value(100.0));

        Mockito.verify(service, Mockito.times(1)).depositCajero(bankTransaction);
    }

    @Test
    void bankTransactionDepositTrasfer() throws Exception{
        Response<BankTransaction> transaccionBancariaGuardada = Response.<BankTransaction>builder().bodyOut(validTransaction)
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build();


        BankTransactionDepositTransfer bankTransaction = new BankTransactionDepositTransfer();


        Mockito.when(service.depositTrasferencia(bankTransaction)).thenReturn(validTransaction);

        mockMvc.perform(post("/bankTransaction/depositTrasfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bodyOut.amount").value(100.0));

        Mockito.verify(service, Mockito.times(1)).depositTrasferencia(bankTransaction);
    }



}