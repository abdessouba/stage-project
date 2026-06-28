package com.app.econservatoire.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.app.econservatoire.dto.CreateMessageEnvelope;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    
    public void handleVerificationEmail(String email, String token){

        CreateMessageEnvelope messageEnvelope = new CreateMessageEnvelope();
        messageEnvelope.setEleveEmail(email);
        messageEnvelope.setToken(token);
        messageEnvelope.setActionName("Email Verification");
        messageEnvelope.setPath("/api/eleve/auth/verify-email");
        messageEnvelope.setMessage("Click The Button Below To Verify Your Account:");
        messageEnvelope.setSubject("Account Verification.");
        sendVerificationMessage(messageEnvelope);
    }


    public void sendVerificationMessage(CreateMessageEnvelope message){
        try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setTo(message.getEleveEmail());
                helper.setSubject(message.getSubject());
                helper.setFrom(from);
                helper.setText(message.getEmailMessageEnvelope(), true);
                mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
