package com.app.econservatoire.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.econservatoire.dto.eleve.EleveRequest;
import com.app.econservatoire.dto.eleve.EleveResponse;
import com.app.econservatoire.models.Eleve;
import com.app.econservatoire.service.EleveService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/eleve/auth")
public class EleveController {

    public final EleveService eleveService;

    @PostMapping("/signup")
    public ResponseEntity<EleveResponse> SignUp(@Valid @RequestBody EleveRequest eleve){
                
        return ResponseEntity.status(HttpStatus.CREATED).body(eleveService.registerEleve(eleve));
    }

    @PostMapping("/signin")
    public String SignIn(@RequestBody String entity) {
        
        return entity;
    }

    @PostMapping("/verify-email")
    public String VerifyEmail(@RequestBody String entity) {
        
        return entity;
    }
    
}
