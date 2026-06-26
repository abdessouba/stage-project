package com.app.econservatoire.controller;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/eleve/auth")
public class EleveController {
    @PostMapping("/signup")
    public String SignUp(@RequestBody String entity) {
                
        return entity;
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
