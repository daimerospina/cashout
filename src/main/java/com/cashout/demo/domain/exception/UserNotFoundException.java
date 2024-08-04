package com.cashout.demo.domain.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String error){
        super(error);
    }
}
