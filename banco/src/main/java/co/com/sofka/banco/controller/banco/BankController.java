package co.com.sofka.banco.controller.banco;

import co.com.sofka.banco.controller.model.response.GenericResponse;
import co.com.sofka.banco.model.entity.Bank;
import co.com.sofka.banco.service.IBankService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class BankController {

    private static final Logger logger = LoggerFactory.getLogger(BankController.class);

    private final IBankService bankService;

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<Bank>>> getAll() {
        logger.info("Buscando todos los bancos");
        return new ResponseEntity<>( GenericResponse.<List<Bank>>builder().bodyOut(bankService.getAll())
                .message("Bancos encontrados").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
