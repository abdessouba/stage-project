package com.app.econservatoire.exceptions.eleve;

public class AccountNotVerifiedException extends RuntimeException {
    public AccountNotVerifiedException(String message){
        super(message);
    }
}
