package com.app.econservatoire.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.econservatoire.dto.eleve.EleveRequest;
import com.app.econservatoire.dto.eleve.EleveSignInReponse;
import com.app.econservatoire.dto.eleve.EleveSignInRequest;
import com.app.econservatoire.dto.eleve.ForgetPasswordRequest;
import com.app.econservatoire.dto.eleve.ResetPasswordRequest;
import com.app.econservatoire.service.EleveService;
import com.app.econservatoire.service.ResetPasswordService;
import com.app.econservatoire.service.TokenVerifyService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public final ResetPasswordService resetPasswordService;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@Valid @RequestBody EleveRequest eleve){
        eleveService.registerEleve(eleve);     
        return ResponseEntity.status(HttpStatus.CREATED).body("Verification email sent successfully.");
    }

    @PostMapping("/signin")
    public ResponseEntity<EleveSignInReponse> login(@RequestBody EleveSignInRequest request,
                                HttpServletResponse response) {
        return ResponseEntity.ok(eleveService.signInUser(request, response));
    }


    @GetMapping("/verify-email")
    public ResponseEntity<String> getMethodName(@RequestParam String token) {
        verificationTokenService.verifyToken(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email Verified Successfully.");
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordRequest request) {
        resetPasswordService.forgetPassword(request.getEmail());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Verification sent to you.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        resetPasswordService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("New password have been update.");
    }
}
