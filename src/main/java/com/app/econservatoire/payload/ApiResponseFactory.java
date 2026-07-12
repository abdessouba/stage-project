package com.app.econservatoire.payload;

import java.time.LocalDateTime;

public class ApiResponseFactory<T> {

    public static <T> ApiResponse<T> success(T data, int status, String message) {
        return ApiResponse.<T>builder()
                .data(data)
                .message(message == null ? "" : message)
                .status(status)
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, int status) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
    }
}