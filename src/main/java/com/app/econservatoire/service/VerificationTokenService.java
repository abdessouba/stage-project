package com.app.econservatoire.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.lang.Long;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.econservatoire.Repository.VerificationTokenRepository;
import com.app.econservatoire.models.Eleve;
import com.app.econservatoire.models.VerificationToken;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    // @Value("${token.verification.expiration-hours}")
    private final Long verificationExpirationHours = 24L;

    private final JavaMailSender mailSender;

    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${spring.mail.username}")
    private String from;

    public VerificationToken createVerificationToken(Eleve eleve){
        // generate token
        String token = UUID.randomUUID().toString();
        // current time + 1h for token validation duration
        LocalDateTime validationTime = LocalDateTime.now().plusHours(verificationExpirationHours);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setEleve(eleve);
        verificationToken.setExpirydate(validationTime);
        verificationToken.setToken(token);

        return verificationTokenRepository.save(verificationToken);
    }

    public void handleVerificationEmail(Eleve eleve){
        String token = createVerificationToken(eleve).getToken();
        sendVerificationMessage(eleve.getEmail(), token, "Email Verification", "/api/eleve/auth/verify-email", "Click The Button Below To Verify Your Account:");
    }

    public void handlePasswordResetEmail(){}

    public void sendVerificationMessage(String eleveEmail, String token, String subject, String path, String message){
        try {
            String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(path)
            .queryParam("token", token)
            .toUriString();

            String content = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px;">
                        <h2 style="color: #333;">%s</h2>
                        <p style="font-size: 16px; color: #555;">%s</p>

                        <a href="%s"
                        style="display:inline-block;
                                padding:12px 20px;
                                margin:20px 0;
                                font-size:16px;
                                color:#ffffff;
                                background-color:#007bff;
                                text-decoration:none;
                                border-radius:5px;">
                            Verify Account
                        </a>

                        <p style="font-size: 14px; color: #777;">Or copy this link:</p>
                        <p style="font-size: 14px; color: #007bff;">%s</p>

                        <p style="font-size: 12px; color: #aaa;">This is an automated message.</p>
                    </div>
                    """.formatted(subject, message, actionUrl, actionUrl);
                
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setTo(eleveEmail);
                helper.setSubject(subject);
                helper.setFrom(from);
                helper.setText(content, true);

                mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void verifyToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
        .orElseThrow(() -> new RuntimeException("Invalid token"));

        
        if (verificationToken.isVerified()) {
            throw new RuntimeException("Token already used");
        }
                
        if (verificationToken.getExpirydate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired. Ask for another one.");
        }

        // update database for validation account
        verificationToken.setVerified(true);
        verificationToken.getEleve().setValide(true);

        verificationTokenRepository.save(verificationToken);

    }
}
