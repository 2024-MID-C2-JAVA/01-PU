package com.example.banco_yoder.controller;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.service.ICuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(CuentaController.class)
public class CuentaControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ICuentaService cuentaService;

    private Cuenta cuenta;

    @BeforeEach
    public void setup() {
        cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setSaldo(1000.0);
    }

    @Test
    public void testDepositoDesdeSucursal() {
        Mockito.when(cuentaService.realizarDepositoDesdeSucursal("1234567890", 100.0))
                .thenReturn(Mono.just(ResponseEntity.ok(cuenta)));

        webTestClient.post()
                .uri("/api/cuentas/deposito/sucursal?numeroCuenta=1234567890&monto=100")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cuenta.class)
                .value(c -> c.getNumeroCuenta().equals("1234567890"));
    }

    @Test
    public void testDepositoDesdeCajero() {
        Mockito.when(cuentaService.realizarDepositoDesdeCajero("1234567890", 100.0))
                .thenReturn(Mono.just(ResponseEntity.ok(cuenta)));

        webTestClient.post()
                .uri("/api/cuentas/deposito/cajero?numeroCuenta=1234567890&monto=100")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cuenta.class)
                .value(c -> c.getNumeroCuenta().equals("1234567890"));
    }

    @Test
    public void testDepositoDesdeOtraCuenta() {
        Mockito.when(cuentaService.realizarDepositoDesdeOtraCuenta("1234567890", 100.0))
                .thenReturn(Mono.just(ResponseEntity.ok(cuenta)));

        webTestClient.post()
                .uri("/api/cuentas/deposito/otraCuenta?numeroCuenta=1234567890&monto=100")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cuenta.class)
                .value(c -> c.getNumeroCuenta().equals("1234567890"));
    }

    @Test
    public void testCompraEnWeb() {
        Mockito.when(cuentaService.realizarCompraEnWeb("1234567890", 50.0))
                .thenReturn(Mono.just(ResponseEntity.ok(cuenta)));

        webTestClient.post()
                .uri("/api/cuentas/compra/web?numeroCuenta=1234567890&monto=50")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cuenta.class)
                .value(c -> c.getNumeroCuenta().equals("1234567890"));
    }

    @Test
    public void testRetiroDesdeCajero() {
        Mockito.when(cuentaService.realizarRetiroDesdeCajero("1234567890", 50.0))
                .thenReturn(Mono.just(ResponseEntity.ok(cuenta)));

        webTestClient.post()
                .uri("/api/cuentas/retiro/cajero?numeroCuenta=1234567890&monto=50")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cuenta.class)
                .value(c -> c.getNumeroCuenta().equals("1234567890"));
    }
}
