package com.app.econservatoire.exceptions.pay;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.econservatoire.exceptions.ApiExceptionResponse;

@RestControllerAdvice
public class PayControllerAdvice {
    @ExceptionHandler(PayNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> payNotFoundException(PayNotFoundException exception){
        ApiExceptionResponse response = new ApiExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
