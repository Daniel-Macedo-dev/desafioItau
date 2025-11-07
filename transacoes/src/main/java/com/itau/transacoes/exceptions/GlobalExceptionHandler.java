package com.itau.transacoes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGeneric(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
