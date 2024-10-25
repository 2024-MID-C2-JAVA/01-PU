package com.example.banco_yoder.service;

import com.example.banco_yoder.domain.Cuenta;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ICuentaService {

    // Métodos para depósitos
    Mono<ResponseEntity<Cuenta>> realizarDepositoDesdeSucursal(String numeroCuenta, Double monto);
    Mono<ResponseEntity<Cuenta>> realizarDepositoDesdeCajero(String numeroCuenta, Double monto);
    Mono<ResponseEntity<Cuenta>> realizarDepositoDesdeOtraCuenta(String numeroCuenta, Double monto);

    // Método para retiro
    Mono<ResponseEntity<Cuenta>> realizarRetiroDesdeCajero(String numeroCuenta, Double monto);

    // Métodos para compras
    Mono<ResponseEntity<Cuenta>> realizarCompraEnEstablecimiento(String numeroCuenta, Double monto);
    Mono<ResponseEntity<Cuenta>> realizarCompraEnWeb(String numeroCuenta, Double monto);

    // Obtener cuenta por número de cuenta
    Mono<Cuenta> obtenerCuentaPorNumero(String numeroCuenta);
}
