package com.cashout.demo.domain.exception;

public class BalanceException extends RuntimeException{
    public BalanceException(String error){
        super(error);
    }
}
