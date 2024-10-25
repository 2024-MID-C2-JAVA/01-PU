package com.example.banco_yoder.controller;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final ICuentaService cuentaService;

    @Autowired
    public CuentaController(ICuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping("/deposito/sucursal")
    public ResponseEntity<Cuenta> depositoDesdeSucursal(@RequestParam String numeroCuenta,
                                                        @RequestParam Double monto) {
        return cuentaService.realizarDepositoDesdeSucursal(numeroCuenta, monto);
    }

    @PostMapping("/deposito/cajero")
    public ResponseEntity<Cuenta> depositoDesdeCajero(@RequestParam String numeroCuenta,
                                                      @RequestParam Double monto) {
        return cuentaService.realizarDepositoDesdeCajero(numeroCuenta, monto);
    }

    @PostMapping("/deposito/otraCuenta")
    public ResponseEntity<Cuenta> depositoDesdeOtraCuenta(@RequestParam String numeroCuenta,
                                                          @RequestParam Double monto) {
        return cuentaService.realizarDepositoDesdeOtraCuenta(numeroCuenta, monto);
    }

    @PostMapping("/compra/establecimiento")
    public ResponseEntity<Cuenta> compraEnEstablecimiento(@RequestParam String numeroCuenta,
                                                          @RequestParam Double monto) {
        return cuentaService.realizarCompraEnEstablecimiento(numeroCuenta, monto);
    }

    @PostMapping("/compra/web")
    public ResponseEntity<Cuenta> compraEnWeb(@RequestParam String numeroCuenta,
                                              @RequestParam Double monto) {
        return cuentaService.realizarCompraEnWeb(numeroCuenta, monto);
    }

    @PostMapping("/retiro/cajero")
    public ResponseEntity<Cuenta> retiroDesdeCajero(@RequestParam String numeroCuenta,
                                                    @RequestParam Double monto) {
        return cuentaService.realizarRetiroDesdeCajero(numeroCuenta, monto);
    }
    @GetMapping("/obtener/{numeroCuenta}")
    public ResponseEntity<Cuenta> obtenerCuentaPorNumero(@PathVariable String numeroCuenta) {
        Cuenta cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
        return ResponseEntity.ok(cuenta);
    }
}
