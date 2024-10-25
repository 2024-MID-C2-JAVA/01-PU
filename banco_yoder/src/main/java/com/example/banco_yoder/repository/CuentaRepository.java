package com.example.banco_yoder.repository;

import com.example.banco_yoder.domain.Cuenta;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CuentaRepository extends ReactiveMongoRepository<Cuenta, String> {

    /**
     * Busco una cuenta por su número de cuenta.
     *
     * @param numeroCuenta el número de cuenta a buscar.
     * @return es Mono que emite la cuenta encontrada o vacío si no existe.
     */
    Mono<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
