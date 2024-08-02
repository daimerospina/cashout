package com.cashout.demo.domain.exception;

public class PaymentRejectedException extends RuntimeException{
    public PaymentRejectedException(String error){
        super(error);
    }
}
