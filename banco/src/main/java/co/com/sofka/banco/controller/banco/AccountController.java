package co.com.sofka.banco.controller.banco;

import co.com.sofka.banco.controller.model.response.GenericResponse;
import co.com.sofka.banco.model.entity.Account;
import co.com.sofka.banco.service.IAccountService;
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
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final IAccountService service;

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<Account>>> getAll() {
        logger.info("Buscando todos los bancos");
        return new ResponseEntity<>( GenericResponse.<List<Account>>builder().bodyOut(service.getAll())
                .message("Bancos encontrados").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
