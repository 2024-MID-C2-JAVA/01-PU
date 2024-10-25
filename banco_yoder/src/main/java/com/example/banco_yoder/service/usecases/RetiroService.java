package com.example.banco_yoder.service.usecases;


import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.repository.CuentaRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RetiroService {

    private final CuentaRepository cuentaRepository;

    public RetiroService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Mono<Cuenta> execute(String numeroCuenta, Double monto, Double costoTransaccion) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .flatMap(cuenta -> {
                    Double nuevoSaldo = cuenta.getSaldo() - monto - costoTransaccion;
                    if (nuevoSaldo < 0) {
                        return Mono.error(new RuntimeException("Saldo insuficiente para realizar el retiro."));
                    }
                    cuenta.setSaldo(nuevoSaldo);
                    return cuentaRepository.save(cuenta);
                });
    }
}
