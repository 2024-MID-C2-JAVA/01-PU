package com.example.banco_yoder.service;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.repository.CuentaRepository;
import com.example.banco_yoder.service.impl.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    private Cuenta cuenta;

    @BeforeEach
    public void setup() {
        cuenta = new Cuenta("1234567890", 1000.0);


        ReflectionTestUtils.setField(cuentaService, "costoDepositoCajero", 2.0);
        ReflectionTestUtils.setField(cuentaService, "costoDepositoOtraCuenta", 1.5);
        ReflectionTestUtils.setField(cuentaService, "costoDepositoSucursal", 0.0);
        ReflectionTestUtils.setField(cuentaService, "costoRetiro", 1.0);
        ReflectionTestUtils.setField(cuentaService, "costoSeguroCompraWeb", 5.0);
    }

    @Test
    public void testRealizarDepositoDesdeSucursal() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaService.realizarDepositoDesdeSucursal("1234567890", 100.0);

        assertEquals(1100.0, response.getBody().getSaldo());
        assertEquals("1234567890", response.getBody().getNumeroCuenta());
    }

    @Test
    public void testRealizarRetiroDesdeCajero() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaService.realizarRetiroDesdeCajero("1234567890", 50.0);

        assertEquals(949.0, response.getBody().getSaldo());
    }

    @Test
    public void testRealizarCompraEnWeb() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaService.realizarCompraEnWeb("1234567890", 50.0);


        assertEquals(945.0, response.getBody().getSaldo());
        assertEquals("1234567890", response.getBody().getNumeroCuenta());
    }


    @Test
    public void testRealizarCompraEnEstablecimiento() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaService.realizarCompraEnEstablecimiento("1234567890", 50.0);

        assertEquals(950.0, response.getBody().getSaldo());
        assertEquals("1234567890", response.getBody().getNumeroCuenta());
    }
    @Test
    public void testRealizarDepositoDesdeOtraCuenta() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaService.realizarDepositoDesdeOtraCuenta("1234567890", 100.0);

        assertEquals(1098.5, response.getBody().getSaldo());
        assertEquals("1234567890", response.getBody().getNumeroCuenta());
    }

    @Test
    public void testObtenerCuentaPorNumero() {
        when(cuentaRepository.findByNumeroCuenta(anyString())).thenReturn(cuenta);
        Cuenta result = cuentaService.obtenerCuentaPorNumero("1234567890");


        assertEquals("1234567890", result.getNumeroCuenta());
        assertEquals(1000.0, result.getSaldo());
    }





}
