package com.cashout.demo.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandle {

    private static final String RETRIES_EXHAUSTED = "Reintentos agotados: " ;

    @ExceptionHandler(PaymentRejectedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<ErrorResponse>> handlerPaymentRejectedException(PaymentRejectedException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(exception.getMessage())));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<ErrorResponse>> handleInsufficientBalanceException(InsufficientBalanceException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(exception.getMessage())));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<ErrorResponse>> handleUserNotFoundException(UserNotFoundException exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneralException2(Exception exception){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(RETRIES_EXHAUSTED + exception.getMessage())));
    }
}
