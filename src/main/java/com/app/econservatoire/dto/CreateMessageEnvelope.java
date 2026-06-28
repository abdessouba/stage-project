package com.app.econservatoire.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMessageEnvelope {
    private String eleveEmail;
    private String token;
    private String subject;
    private String path;
    private String message;
    private String actionName;

    public String getEmailMessageEnvelope(){
        return  """
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
                        %s
                    </a>

                    <p style="font-size: 14px; color: #777;">Or copy this link:</p>
                    <p style="font-size: 14px; color: #007bff;">%s</p>

                    <p style="font-size: 12px; color: #aaa;">This is an automated message.</p>
                </div>
                
            """.formatted(subject, message, getCurrentUrl(), actionName, getCurrentUrl());
    }

    public String getCurrentUrl(){
        // This will Dynamically get my application base URL http://localhost:8080. 
        String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(path)
            .queryParam("token", token)
            .toUriString();
        return actionUrl;
    }
}
