package com.example.banco_yoder.exception;

public class CuentaNoEncontradaException extends RuntimeException {

    /**
     * Constructor que crea la excepción con un mensaje personalizado.
     *
     * @param numeroCuenta el número de cuenta que no fue encontrado.
     */
    public CuentaNoEncontradaException(String numeroCuenta) {
        super("La cuenta con número " + numeroCuenta + " no fue encontrada.");
    }
}
