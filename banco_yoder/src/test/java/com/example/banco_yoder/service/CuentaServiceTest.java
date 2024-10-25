package com.example.banco_yoder.service;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.repository.CuentaRepository;
import com.example.banco_yoder.service.impl.CuentaServiceImpl;
import com.example.banco_yoder.service.usecases.DepositoService;
import com.example.banco_yoder.service.usecases.RetiroService;
import com.example.banco_yoder.service.usecases.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class CuentaServiceTest {

    private CuentaServiceImpl cuentaService;
    private DepositoService depositoService;
    private RetiroService retiroService;
    private CompraService compraService;
    private Cuenta cuenta;

    @BeforeEach
    public void setup() {
        depositoService = Mockito.mock(DepositoService.class);
        retiroService = Mockito.mock(RetiroService.class);
        compraService = Mockito.mock(CompraService.class);
        cuentaService = new CuentaServiceImpl(depositoService, retiroService, compraService);

        cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setSaldo(1000.0);
    }

    @Test
    public void testRealizarDepositoDesdeSucursal() {
        Mockito.when(depositoService.execute(eq("1234567890"), eq(100.0), eq(0.0)))
                .thenReturn(Mono.just(cuenta));

        Mono<ResponseEntity<Cuenta>> result = cuentaService.realizarDepositoDesdeSucursal("1234567890", 100.0);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody() != null &&
                        response.getBody().getNumeroCuenta().equals("1234567890"))
                .verifyComplete();
    }

    @Test
    public void testRealizarRetiroDesdeCajero() {
        Mockito.when(retiroService.execute(eq("1234567890"), eq(50.0), eq(1.0)))
                .thenReturn(Mono.just(cuenta));

        Mono<ResponseEntity<Cuenta>> result = cuentaService.realizarRetiroDesdeCajero("1234567890", 50.0);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody() != null &&
                        response.getBody().getNumeroCuenta().equals("1234567890"))
                .verifyComplete();
    }

    @Test
    public void testRealizarCompraEnWeb() {
        Mockito.when(compraService.execute(eq("1234567890"), eq(50.0), eq(5.0)))
                .thenReturn(Mono.just(cuenta));

        Mono<ResponseEntity<Cuenta>> result = cuentaService.realizarCompraEnWeb("1234567890", 50.0);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody() != null &&
                        response.getBody().getNumeroCuenta().equals("1234567890"))
                .verifyComplete();
    }
}
