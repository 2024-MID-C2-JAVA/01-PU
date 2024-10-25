package com.example.banco_yoder.service.usecases;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.repository.CuentaRepository;
import org.springframework.stereotype.Component;

@Component
public class DepositoService {

    private final CuentaRepository cuentaRepository;

    public DepositoService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta execute(String numeroCuenta, Double monto, Double costoTransaccion) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);

        if (cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada.");
        }

        Double nuevoSaldo = cuenta.getSaldo() + monto - costoTransaccion;
        cuenta.setSaldo(nuevoSaldo);
        return cuentaRepository.save(cuenta);
    }
}
