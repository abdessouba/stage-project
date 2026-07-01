package com.app.econservatoire.exceptions.eleve;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.econservatoire.exceptions.ApiExceptionResponse;

@RestControllerAdvice
public class EleveControllerAdvice {
    @ExceptionHandler(EleveAuthenticationException.class)
    public ResponseEntity<ApiExceptionResponse> eleveUnauthorizedException(EleveAuthenticationException exception){
        ApiExceptionResponse response = new ApiExceptionResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ApiExceptionResponse> eleveAlreadyExist(TokenInvalidException exception){
        ApiExceptionResponse response = new ApiExceptionResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    } 
}
