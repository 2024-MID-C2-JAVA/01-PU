package co.com.sofka.banco.controller.banco;

import co.com.sofka.banco.controller.model.response.GenericResponse;
import co.com.sofka.banco.model.entity.TypeTransaction;
import co.com.sofka.banco.service.ITypeTransactionService;
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
@RequestMapping("/typeTransaction")
@AllArgsConstructor
public class TypeTransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TypeTransactionController.class);

    private final ITypeTransactionService service;

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<TypeTransaction>>> getAll() {
        logger.info("Buscando todos los bancos");
        return new ResponseEntity<>( GenericResponse.<List<TypeTransaction>>builder().bodyOut(service.getAll())
                .message("Bancos encontrados").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
