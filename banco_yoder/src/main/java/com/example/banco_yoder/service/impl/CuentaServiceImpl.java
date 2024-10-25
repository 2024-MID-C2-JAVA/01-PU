package com.example.banco_yoder.service.impl;


import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.service.ICuentaService;
import com.example.banco_yoder.service.usecases.DepositoService;
import com.example.banco_yoder.service.usecases.RetiroService;
import com.example.banco_yoder.service.usecases.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CuentaServiceImpl implements ICuentaService {

    private final DepositoService depositoService;
    private final RetiroService retiroService;
    private final CompraService compraService;

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

    @Value("${app.costoCompraEstablecimiento}")
    private Double costoCompraEstablecimiento;

    @Autowired
    public CuentaServiceImpl(DepositoService depositoService, RetiroService retiroService, CompraService compraService) {
        this.depositoService = depositoService;
        this.retiroService = retiroService;
        this.compraService = compraService;
    }

    @Override
    public Mono<ResponseEntity<Cuenta>> realizarDepositoDesdeSucursal(String numeroCuenta, Double monto) {
        return depositoService.execute(numeroCuenta, monto, costoDepositoSucursal)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Override
    public Mono<ResponseEntity<Cuenta>> realizarDepositoDesdeCajero(String numeroCuenta, Double monto) {
        return depositoService.execute(numeroCuenta, monto, costoDepositoCajero)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Override
    public Mono<ResponseEntity<Cuenta>> realizarDepositoDesdeOtraCuenta(String numeroCuenta, Double monto) {
        return depositoService.execute(numeroCuenta, monto, costoDepositoOtraCuenta)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Override
    public Mono<ResponseEntity<Cuenta>> realizarCompraEnEstablecimiento(String numeroCuenta, Double monto) {
        return compraService.execute(numeroCuenta, monto, costoCompraEstablecimiento)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Override
    public Mono<ResponseEntity<Cuenta>> realizarCompraEnWeb(String numeroCuenta, Double monto) {
        return compraService.execute(numeroCuenta, monto, costoSeguroCompraWeb)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Override
    public Mono<ResponseEntity<Cuenta>> realizarRetiroDesdeCajero(String numeroCuenta, Double monto) {
        return retiroService.execute(numeroCuenta, monto, costoRetiro)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Override
    public Mono<Cuenta> obtenerCuentaPorNumero(String numeroCuenta) {
        return depositoService.obtenerCuentaPorNumero(numeroCuenta);
    }
}
