package com.app.econservatoire.dto.eleve;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ForgetPasswordRequest {
    @NotBlank(message = "Email required.")
    @Email
    private String email;
}
