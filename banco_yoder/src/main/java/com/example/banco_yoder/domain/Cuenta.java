package com.example.banco_yoder.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cuentas")
public class Cuenta {

    @Id
    private String id;  // Identificador único generado por MongoDB

    private String usuario;  // Nombre del titular de la cuenta

    private String numeroCuenta;  // Número de la cuenta bancaria

    private Double saldo;  // Saldo actual de la cuenta
}
