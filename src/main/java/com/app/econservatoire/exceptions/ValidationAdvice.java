package com.app.econservatoire.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

		final Map<String, String> fieldErrors = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach((field)->{
            fieldErrors.put(field.getField(), field.getDefaultMessage());
        });

		final ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(fieldErrors, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

		return ResponseEntity.status(validationErrorResponse.getStatus()).body(validationErrorResponse);
	}

}
