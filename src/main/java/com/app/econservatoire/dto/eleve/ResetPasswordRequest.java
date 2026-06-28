package com.app.econservatoire.dto.eleve;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
