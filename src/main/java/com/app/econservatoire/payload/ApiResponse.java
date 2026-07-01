package com.app.econservatoire.payload;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private T data;
    private Integer status;
    private String message;
    private boolean success;
    private LocalDateTime timestamp;
}
