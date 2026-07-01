package com.app.econservatoire.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.econservatoire.Repository.PayRepository;
import com.app.econservatoire.models.Pay;
import com.app.econservatoire.payload.ApiResponse;
import com.app.econservatoire.payload.ApiResponseFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PayController {

    private final PayRepository payRepository;

    @GetMapping("/pays")
    public ResponseEntity<ApiResponse<List<Pay>>> getAllPays() {
        List<Pay> pays = payRepository.findAll();
        return ResponseEntity.ok(ApiResponseFactory.success(pays, HttpStatus.OK.value(), ""));
    }
    
}
