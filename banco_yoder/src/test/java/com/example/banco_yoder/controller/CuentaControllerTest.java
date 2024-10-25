package com.example.banco_yoder.controller;

import com.example.banco_yoder.domain.Cuenta;
import com.example.banco_yoder.service.ICuentaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CuentaController.class)
@ActiveProfiles("test")
public class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICuentaService cuentaService;

    @Test
    public void testDepositoDesdeSucursal() throws Exception {
        Cuenta cuenta = new Cuenta("1234567890", 1100.0);
        Mockito.when(cuentaService.realizarDepositoDesdeSucursal("1234567890", 100.0))
                .thenReturn(ResponseEntity.ok(cuenta));

        mockMvc.perform(post("/api/cuentas/deposito/sucursal")
                        .param("numeroCuenta", "1234567890")
                        .param("monto", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("1234567890"))
                .andExpect(jsonPath("$.saldo").value(1100.0));
    }


    @Test
    public void testRetiroDesdeCajero() throws Exception {
        Cuenta cuenta = new Cuenta("1234567890", 950.0);
        Mockito.when(cuentaService.realizarRetiroDesdeCajero("1234567890", 50.0))
                .thenReturn(ResponseEntity.ok(cuenta));

        mockMvc.perform(post("/api/cuentas/retiro/cajero")
                        .param("numeroCuenta", "1234567890")
                        .param("monto", "50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saldo").value(950.0));
    }
    @Test
    public void testObtenerCuentaPorNumero() throws Exception {
        Cuenta cuenta = new Cuenta("1234567890", 1000.0);
        Mockito.when(cuentaService.obtenerCuentaPorNumero("1234567890")).thenReturn(cuenta);

        mockMvc.perform(get("/api/cuentas/obtener/1234567890")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("1234567890"))
                .andExpect(jsonPath("$.saldo").value(1000.0));
    }
    @Test
    public void testDepositoDesdeCajero() throws Exception {
        Cuenta cuenta = new Cuenta("1234567890", 1050.0);
        Mockito.when(cuentaService.realizarDepositoDesdeCajero("1234567890", 50.0))
                .thenReturn(ResponseEntity.ok(cuenta));

        mockMvc.perform(post("/api/cuentas/deposito/cajero")
                        .param("numeroCuenta", "1234567890")
                        .param("monto", "50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("1234567890"))
                .andExpect(jsonPath("$.saldo").value(1050.0));
    }


    @Test
    public void testCompraEnEstablecimiento() throws Exception {
        Cuenta cuenta = new Cuenta("1234567890", 950.0);
        Mockito.when(cuentaService.realizarCompraEnEstablecimiento("1234567890", 50.0))
                .thenReturn(ResponseEntity.ok(cuenta));

        mockMvc.perform(post("/api/cuentas/compra/establecimiento")
                        .param("numeroCuenta", "1234567890")
                        .param("monto", "50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("1234567890"))
                .andExpect(jsonPath("$.saldo").value(950.0));
    }

}
