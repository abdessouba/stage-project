package com.app.econservatoire.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.econservatoire.Repository.PayRepository;
import com.app.econservatoire.models.Pay;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PayController {

    private final PayRepository payRepository;

    @GetMapping("/pays")
    public List<Pay> getMethodName() {
        List<Pay> pays = payRepository.findAll();
        return pays;
    }
    
}
