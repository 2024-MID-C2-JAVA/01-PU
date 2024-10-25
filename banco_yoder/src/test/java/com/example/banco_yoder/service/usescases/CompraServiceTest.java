package com.example.banco_yoder.service.usescases;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.repository.CuentaRepository;
import com.example.banco_yoder.service.usecases.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CompraService compraService;

    private Cuenta cuenta;

    @BeforeEach
    public void setup() {
        cuenta = new Cuenta("1234567890", 1000.0);
    }



    @Test
    public void testCompraCuentaNoEncontrada() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            compraService.execute("1234567890", 100.0, 5.0);
        });
    }

    @Test
    public void testCompraSaldoInsuficiente() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(cuenta);

        assertThrows(RuntimeException.class, () -> {
            compraService.execute("1234567890", 1000.0, 50.0);
        });
    }
}
