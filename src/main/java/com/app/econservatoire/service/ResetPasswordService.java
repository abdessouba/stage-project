package com.app.econservatoire.service;

import com.app.econservatoire.Repository.ResetPasswordRepository;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.econservatoire.Repository.EleveRepository;
import com.app.econservatoire.dto.CreateMessageEnvelope;
import com.app.econservatoire.dto.eleve.ResetPasswordRequest;
import com.app.econservatoire.exceptions.eleve.TokenInvalidException;
import com.app.econservatoire.exceptions.eleve.EleveAuthenticationException;
import com.app.econservatoire.models.Eleve;
import com.app.econservatoire.models.ResetPassword;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    private final ResetPasswordRepository resetPasswordRepository;
    private final EleveRepository eleveRepository;
    private final SendEmailService sendEmailService;
    public final PasswordEncoder passwordEncoder;

    private final Long verificationExpirationHours = 24L;


    public void forgetPassword(String email){
        Eleve eleve = eleveRepository.findByEmail(email).orElseThrow(()-> new EleveAuthenticationException("User With This Email does not exist."));

        String token = UUID.randomUUID().toString();

        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setDate_expiration(LocalDateTime.now().plusHours(verificationExpirationHours));
        resetPassword.setEleve(eleve);
        resetPassword.setToken(token);
        resetPasswordRepository.save(resetPassword);

        CreateMessageEnvelope message = new CreateMessageEnvelope();
        message.setToken(token);
        message.setActionName("Password Reset");
        message.setEleveEmail(email);
        message.setMessage("Click the button to recover your password");
        message.setPath("/api/eleve/auth/reset-password");
        message.setSubject("Password Reset");
        sendEmailService.sendVerificationMessage(message);
    }

    public void resetPassword(@Valid ResetPasswordRequest request){
        ResetPassword resetPassword = resetPasswordRepository.findByToken(request.getToken())
        .orElseThrow(()-> new TokenInvalidException("Invalid reset link. Please request a new password reset email."));

        if(resetPassword.isUsed()){
            throw new TokenInvalidException("This link have been used. Please request a new password reset email.");
        }

        if (resetPassword.getDate_expiration().isBefore(LocalDateTime.now())) {
            throw new TokenInvalidException("This link expired. Please request a new password reset email.");
        }

        //encrypt the new password before saving
        String hashedPassword = passwordEncoder.encode(request.getNewPassword());
        resetPassword.getEleve().setPassword(hashedPassword);

        resetPassword.setUsed(true);
        resetPasswordRepository.save(resetPassword);
    }

}
