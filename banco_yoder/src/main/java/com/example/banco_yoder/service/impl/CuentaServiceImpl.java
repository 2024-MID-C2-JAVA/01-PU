package com.example.banco_yoder.service.impl;

import com.example.banco_yoder.repository.CuentaRepository;
import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.exception.CuentaNoEncontradaException;
import com.example.banco_yoder.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CuentaServiceImpl implements ICuentaService {

    private final CuentaRepository cuentaRepository;

    @Value("${app.costoDepositoCajero}")
    private Double costoDepositoCajero;

    @Value("${app.costoDepositoOtraCuenta}")
    private Double costoDepositoOtraCuenta;

    @Value("${app.costoDepositoSucursal}")
    private Double costoDepositoSucursal;

    @Value("${app.costoRetiro}")
    private Double costoRetiro;

    @Value("${app.costoSeguroCompraWeb}")
    private Double costoSeguroCompraWeb;

    @Autowired
    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public ResponseEntity<Cuenta> realizarDepositoDesdeSucursal(String numeroCuenta, Double monto) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException("La cuenta con número " + numeroCuenta + " no fue encontrada.");
        }


        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }

        return realizarDeposito(numeroCuenta, monto, costoDepositoSucursal);
    }

    @Override
    public ResponseEntity<Cuenta> realizarDepositoDesdeCajero(String numeroCuenta, Double monto) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (monto > cuenta.getSaldo()) {
            throw new IllegalArgumentException("El monto excede el saldo disponible.");
        }
        return realizarDeposito(numeroCuenta, monto, costoDepositoCajero);
    }

    @Override
    public ResponseEntity<Cuenta> realizarDepositoDesdeOtraCuenta(String numeroCuenta, Double monto) {
        return realizarDeposito(numeroCuenta, monto, costoDepositoOtraCuenta);
    }

    private ResponseEntity<Cuenta> realizarDeposito(String numeroCuenta, Double monto, Double costoTransaccion) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException(numeroCuenta);
        }
        Double nuevoSaldo = cuenta.getSaldo() + monto - costoTransaccion;
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
        return ResponseEntity.ok(cuenta);
    }

    @Override
    public ResponseEntity<Cuenta> realizarRetiroDesdeCajero(String numeroCuenta, Double monto) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Double nuevoSaldo = cuenta.getSaldo() - monto - costoRetiro;
        if (nuevoSaldo < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuenta);
        }
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
        return ResponseEntity.ok(cuenta);
    }

    @Override
    public ResponseEntity<Cuenta> realizarCompraEnEstablecimiento(String numeroCuenta, Double monto) {
        return realizarCompra(numeroCuenta, monto, 0.0);
    }

    @Override
    public ResponseEntity<Cuenta> realizarCompraEnWeb(String numeroCuenta, Double monto) {
        return realizarCompra(numeroCuenta, monto, costoSeguroCompraWeb);
    }

    private ResponseEntity<Cuenta> realizarCompra(String numeroCuenta, Double monto, Double costoSeguro) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException(numeroCuenta);
        }
        Double nuevoSaldo = cuenta.getSaldo() - monto - costoSeguro;
        if (nuevoSaldo < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuenta);
        }
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
        return ResponseEntity.ok(cuenta);
    }

    @Override
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException(numeroCuenta);
        }
        return cuenta;
    }
}
