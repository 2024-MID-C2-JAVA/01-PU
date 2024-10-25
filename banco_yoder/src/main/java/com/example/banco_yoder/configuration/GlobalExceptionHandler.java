package com.example.banco_yoder.configuration;

import com.example.banco_yoder.exception.CuentaNoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manejo la excepción CuentaNoEncontradaException.
     *
     * @param ex la excepción lanzada.
     * @return una respuesta 404 con el mensaje de error.
     */
    @ExceptionHandler(CuentaNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleCuentaNoEncontrada(CuentaNoEncontradaException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    /**
     * Manejo excepciones generales de tipo RuntimeException.
     *
     * @param ex la excepción lanzada.
     * @return una respuesta 500 con el mensaje de error.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity<String>> handleRuntimeException(RuntimeException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + ex.getMessage()));
    }
}
