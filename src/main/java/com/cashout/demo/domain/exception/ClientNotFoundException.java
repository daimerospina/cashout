package com.cashout.demo.domain.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(String error){
        super(error);
    }
}
