package com.example.banco_yoder.service.usecases;


import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.repository.CuentaRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DepositoService {

    private final CuentaRepository cuentaRepository;

    public DepositoService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Mono<Cuenta> execute(String numeroCuenta, Double monto, Double costoTransaccion) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .flatMap(cuenta -> {
                    Double nuevoSaldo = cuenta.getSaldo() + monto - costoTransaccion;
                    cuenta.setSaldo(nuevoSaldo);
                    return cuentaRepository.save(cuenta);
                });
    }

    public Mono<Cuenta> obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")));
    }
}
