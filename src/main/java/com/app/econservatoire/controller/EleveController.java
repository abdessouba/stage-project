package com.app.econservatoire.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.econservatoire.dto.eleve.EleveRequest;
import com.app.econservatoire.service.EleveService;
import com.app.econservatoire.service.TokenVerifyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/eleve/auth")
public class EleveController {

    public final EleveService eleveService;
    public final TokenVerifyService verificationTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@Valid @RequestBody EleveRequest eleve){
        eleveService.registerEleve(eleve);     
        return ResponseEntity.status(HttpStatus.CREATED).body("Verification email sent successfully.");
    }

    @PostMapping("/signin")
    public String SignIn(@RequestBody String entity) {
        
        return entity;
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> getMethodName(@RequestParam String token) {
        verificationTokenService.verifyToken(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email Verified Successfully.");
    }
    
    
}
