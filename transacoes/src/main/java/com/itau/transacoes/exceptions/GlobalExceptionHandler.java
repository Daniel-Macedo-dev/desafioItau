package com.itau.transacoes.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<Void> handleTransacaoInvalida(TransacaoInvalidaException exception){
        return ResponseEntity.unprocessableEntity().build();
    }
    @ExceptionHandler(ValorNegativoException.class)
    public ResponseEntity<Void> handleValorNegativo(ValorNegativoException exception){
        return ResponseEntity.unprocessableEntity().build();
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleMessageNotReadable(HttpMessageNotReadableException exception){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGeneric(Exception exception){
        return ResponseEntity.internalServerError().build();
    }
}
