package com.app.econservatoire.dto.eleve;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EleveSignInRequest {
    private String identifier;
    private String password;
}
