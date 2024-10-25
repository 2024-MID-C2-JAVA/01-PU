package com.example.banco_yoder.repository;

import com.example.banco_yoder.domain.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, String> {
    Cuenta findByNumeroCuenta(String numeroCuenta);


}
