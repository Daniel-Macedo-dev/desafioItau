package com.itau.transacoes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<Void> handleTransacaoInvalida(TransacaoInvalidaException exception){
        return ResponseEntity.unprocessableEntity().build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGeneric(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
