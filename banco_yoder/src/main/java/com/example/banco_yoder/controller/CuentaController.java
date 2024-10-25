package com.example.banco_yoder.controller;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final ICuentaService cuentaService;

    @Autowired
    public CuentaController(ICuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping("/deposito/sucursal")
    public Mono<ResponseEntity<Cuenta>> depositoDesdeSucursal(@RequestParam(required = false) String numeroCuenta,
                                                              @RequestParam(required = false) Double monto) {
        return cuentaService.realizarDepositoDesdeSucursal(numeroCuenta, monto);
    }

    @PostMapping("/deposito/cajero")
    public Mono<ResponseEntity<Cuenta>> depositoDesdeCajero(@RequestParam(required = false) String numeroCuenta,
                                                            @RequestParam(required = false) Double monto) {
        return cuentaService.realizarDepositoDesdeCajero(numeroCuenta, monto);
    }

    @PostMapping("/deposito/otraCuenta")
    public Mono<ResponseEntity<Cuenta>> depositoDesdeOtraCuenta(@RequestParam(required = false) String numeroCuenta,
                                                                @RequestParam(required = false) Double monto) {
        return cuentaService.realizarDepositoDesdeOtraCuenta(numeroCuenta, monto);
    }

    @PostMapping("/compra/establecimiento")
    public Mono<ResponseEntity<Cuenta>> compraEnEstablecimiento(@RequestParam(required = false) String numeroCuenta,
                                                                @RequestParam(required = false) Double monto) {
        return cuentaService.realizarCompraEnEstablecimiento(numeroCuenta, monto);
    }

    @PostMapping("/compra/web")
    public Mono<ResponseEntity<Cuenta>> compraEnWeb(@RequestParam(required = false) String numeroCuenta,
                                                    @RequestParam(required = false) Double monto) {
        return cuentaService.realizarCompraEnWeb(numeroCuenta, monto);
    }

    @PostMapping("/retiro/cajero")
    public Mono<ResponseEntity<Cuenta>> retiroDesdeCajero(@RequestParam(required = false) String numeroCuenta,
                                                          @RequestParam(required = false) Double monto) {
        return cuentaService.realizarRetiroDesdeCajero(numeroCuenta, monto);
    }
}
