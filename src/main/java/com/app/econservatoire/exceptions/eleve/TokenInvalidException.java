package com.app.econservatoire.exceptions.eleve;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message){
        super(message);
    }
}
