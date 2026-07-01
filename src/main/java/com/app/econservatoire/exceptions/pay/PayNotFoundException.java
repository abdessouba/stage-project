package com.app.econservatoire.exceptions.pay;

public class PayNotFoundException extends RuntimeException{
    public PayNotFoundException(String message){
        super(message);
    }
}
