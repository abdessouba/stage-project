package com.app.econservatoire.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiExceptionResponse {
    private String error;
    private Integer status;
    private LocalDateTime timestamp;
}
