package com.example.banco_yoder.service.usecases;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.repository.CuentaRepository;
import org.springframework.stereotype.Component;

@Component
public class CompraService {

    private final CuentaRepository cuentaRepository;

    public CompraService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta execute(String numeroCuenta, Double monto, Double costoSeguro) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);

        if (cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada.");
        }

        Double nuevoSaldo = cuenta.getSaldo() - monto - costoSeguro;

        if (nuevoSaldo < 0) {
            throw new RuntimeException("Saldo insuficiente para realizar la compra.");
        }

        cuenta.setSaldo(nuevoSaldo);
        return cuentaRepository.save(cuenta);
    }
}
