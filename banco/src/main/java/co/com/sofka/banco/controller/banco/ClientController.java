package co.com.sofka.banco.controller.banco;

import co.com.sofka.banco.controller.model.response.GenericResponse;
import co.com.sofka.banco.model.entity.Client;
import co.com.sofka.banco.service.IClientService;
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
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final IClientService servide;

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<Client>>> getAll() {
        logger.info("Buscando todos los bancos");
        return new ResponseEntity<>( GenericResponse.<List<Client>>builder().bodyOut(servide.getAll())
                .message("Bancos encontrados").code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
