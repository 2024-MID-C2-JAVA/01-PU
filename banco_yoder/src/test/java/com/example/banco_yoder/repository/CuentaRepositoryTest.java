package com.example.banco_yoder.repository;

import com.example.banco_yoder.domain.Cuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ActiveProfiles("test")
public class CuentaRepositoryTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testFindByNumeroCuenta() {
        Cuenta cuenta = new Cuenta("1234567890", 1000.0);
        mongoTemplate.save(cuenta, "cuentas");

        Cuenta found = cuentaRepository.findByNumeroCuenta("1234567890");
        assertEquals("1234567890", found.getNumeroCuenta());
        assertEquals(1000.0, found.getSaldo());
    }
}
