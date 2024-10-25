package com.example.banco_yoder.service;

import com.example.banco_yoder.domain.Cuenta;
import org.springframework.http.ResponseEntity;

public interface ICuentaService {

    // Métodos para depósitos
    ResponseEntity<Cuenta> realizarDepositoDesdeSucursal(String numeroCuenta, Double monto);
    ResponseEntity<Cuenta> realizarDepositoDesdeCajero(String numeroCuenta, Double monto);
    ResponseEntity<Cuenta> realizarDepositoDesdeOtraCuenta(String numeroCuenta, Double monto);

    // Método para retiro
    ResponseEntity<Cuenta> realizarRetiroDesdeCajero(String numeroCuenta, Double monto);

    // Métodos para compras
    ResponseEntity<Cuenta> realizarCompraEnEstablecimiento(String numeroCuenta, Double monto);
    ResponseEntity<Cuenta> realizarCompraEnWeb(String numeroCuenta, Double monto);

    // Obtener cuenta por número de cuenta
    Cuenta obtenerCuentaPorNumero(String numeroCuenta);
}
