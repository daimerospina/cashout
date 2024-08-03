package com.cashout.demo.controller;

import com.cashout.demo.domain.exception.BalanceException;
import com.cashout.demo.domain.exception.ClientNotFoundException;
import com.cashout.demo.domain.exception.PaymentRejectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(PaymentRejectedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handlerPaymentRejectedException(PaymentRejectedException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage()));
    }

    @ExceptionHandler(BalanceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleBalanceException(BalanceException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleBalanceException(ClientNotFoundException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleBalanceException2(Exception exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reintentos agotados: " + exception.getMessage()));
    }
}
