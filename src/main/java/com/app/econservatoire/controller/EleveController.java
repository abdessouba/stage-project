package com.app.econservatoire.controller;

import org.springframework.web.bind.annotation.RestController;

import com.app.econservatoire.dto.eleve.EleveRegistrationRequest;
import com.app.econservatoire.dto.eleve.EleveSignInRequest;
import com.app.econservatoire.dto.eleve.ForgetPasswordRequest;
import com.app.econservatoire.dto.eleve.ResetPasswordRequest;
import com.app.econservatoire.payload.ApiResponse;
import com.app.econservatoire.payload.ApiResponseFactory;
import com.app.econservatoire.service.EleveAuthService;
import com.app.econservatoire.service.ResetPasswordService;
import com.app.econservatoire.service.TokenVerifyService;

import jakarta.servlet.http.HttpServletResponse;
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

    public final EleveAuthService eleveService;
    public final TokenVerifyService verificationTokenService;
    public final ResetPasswordService resetPasswordService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> SignUp(@Valid @RequestBody EleveRegistrationRequest eleve){
        eleveService.registerEleve(eleve);     
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseFactory.success(null, HttpStatus.CREATED.value(), "Account created."));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody EleveSignInRequest request,
                                HttpServletResponse response) {
        eleveService.signInUser(request, response);
        return ResponseEntity.ok(ApiResponseFactory.success(null, HttpStatus.OK.value(), "You are signed in."));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<Void>> getMethodName(@RequestParam String token) {
        verificationTokenService.verifyToken(token);
        return ResponseEntity.ok(ApiResponseFactory.success(null, HttpStatus.OK.value(), "Email verified successfully."));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ApiResponse<Void>> forgetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        resetPasswordService.forgetPassword(request.getEmail());
        return ResponseEntity.ok(ApiResponseFactory.success(null, HttpStatus.OK.value(), "Reset password email sent to you."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordService.resetPassword(request);
        return ResponseEntity.ok(ApiResponseFactory.success(null, HttpStatus.OK.value(), "Your password have been updated."));
    }

}
