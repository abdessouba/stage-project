package com.app.econservatoire.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.econservatoire.Repository.VerificationTokenRepository;
import com.app.econservatoire.configuration.AppProperties;
import com.app.econservatoire.dto.CreateMessageEnvelope;
import com.app.econservatoire.exceptions.eleve.TokenInvalidException;
import com.app.econservatoire.models.Eleve;
import com.app.econservatoire.models.VerificationToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenVerifyService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final SendEmailService sendEmailService;
    private final AppProperties appProperties;

    private final Long verificationExpirationHours = 24L;

    public VerificationToken createVerificationToken(Eleve eleve){
        // generate token
        String token = UUID.randomUUID().toString();
        // current time + 1h for token validation duration
        LocalDateTime experationTime = LocalDateTime.now().plusHours(verificationExpirationHours);

        // add verification token to database
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setEleve(eleve);
        verificationToken.setExpirydate(experationTime);
        verificationToken.setToken(token);
        return verificationTokenRepository.save(verificationToken);
    }

    public void verifyToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
        .orElseThrow(() -> new TokenInvalidException("Invalid Token."));

        if (verificationToken.isVerified()) {
            throw new TokenInvalidException("This account already verified.");
        }
                
        if (verificationToken.getExpirydate().isBefore(LocalDateTime.now())) {
            throw new TokenInvalidException("Your verification link is no longer valid. Please request a new verification email.");
        }

        // update database validate and verified fields after account verification
        verificationToken.setVerified(true);
        verificationToken.getEleve().setValide(true);

        verificationTokenRepository.save(verificationToken);
    }

    public void sendVerificationEmail(Eleve eleve){
        VerificationToken token = createVerificationToken(eleve);

        CreateMessageEnvelope messageEnvelope = new CreateMessageEnvelope();
        messageEnvelope.setEleveEmail(eleve.getEmail());
        messageEnvelope.setToken(token.getToken());
        messageEnvelope.setActionName("Email Verification");
        messageEnvelope.setMessage("Click The Button Below To Verify Your Account:");
        messageEnvelope.setSubject("Account Verification.");
        messageEnvelope.setUrl(appProperties.getUrl());
        messageEnvelope.setPath(appProperties.getVerifyPath());
        sendEmailService.sendVerificationMessage(messageEnvelope);
    }
}
