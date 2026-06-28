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
