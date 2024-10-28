package co.com.sofka.banktransaction.controller.app;


import co.com.sofka.banktransaction.controller.dto.Response;
import co.com.sofka.banktransaction.controller.dto.request.*;
import co.com.sofka.banktransaction.model.entity.BankTransaction;
import co.com.sofka.banktransaction.service.IBankTransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bankTransaction")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/test")
    public ResponseEntity<Response<String>> tets() {
        logger.info("Test");
        return new ResponseEntity<>( Response.<String>builder().bodyOut("test")
                .message("test").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
    private final IBankTransactionService service;

    public MainController(IBankTransactionService service) {
        this.service = service;
    }



    @GetMapping("/all")
    public ResponseEntity<Response<List<BankTransaction>>> getAll() {
        logger.info("Buscando todos los bancos");
        return new ResponseEntity<>( Response.<List<BankTransaction>>builder().bodyOut(service.getAll())
                .message("Bancos encontrados").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }



    @PostMapping("/retitarCajero")
    public ResponseEntity<Response<BankTransaction>> bankTransactionWithdrawFromATM(@Valid @RequestBody BankTransactionWithdrawFromATM bankTransaction) {
        logger.info("Guardando transaccion bancaria");
        return new ResponseEntity<>( Response.<BankTransaction>builder().bodyOut(service.withdrawFromATM(bankTransaction))
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }


    @PostMapping("/buys")
    public ResponseEntity<Response<BankTransaction>> bankTransactionBuys(@Valid @RequestBody BankTransactionBuys item) {
        logger.info("Guardando transaccion bancaria");
        return new ResponseEntity<>( Response.<BankTransaction>builder().bodyOut(service.buys(item))
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }




    @PostMapping("/deposit")
    public ResponseEntity<Response<BankTransaction>> bankTransactionDeposit(@Valid @RequestBody BankTransactionDepositSucursal item) {
        logger.info("Guardando transaccion bancaria");
        return new ResponseEntity<>( Response.<BankTransaction>builder().bodyOut(service.depositSucursal(item))
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }


    @PostMapping("/depositCajero")
    public ResponseEntity<Response<BankTransaction>> bankTransactionDepositCajero(@Valid @RequestBody BankTransactionDepositCajero item) {
        logger.info("Guardando transaccion bancaria");
        return new ResponseEntity<>( Response.<BankTransaction>builder().bodyOut(service.depositCajero(item))
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }


    @PostMapping("/depositTrasfer")
    public ResponseEntity<Response<BankTransaction>> bankTransactionDepositTrasfer(@Valid @RequestBody BankTransactionDepositTransfer item) {
        logger.info("Guardando transaccion bancaria");
        return new ResponseEntity<>( Response.<BankTransaction>builder().bodyOut(service.depositTrasferencia(item))
                .message("Transaccion bancaria guardada").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
