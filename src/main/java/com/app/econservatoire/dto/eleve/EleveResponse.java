package com.app.econservatoire.dto.eleve;

import com.app.econservatoire.models.Pay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EleveResponse {
    private String nom;
    private String prenom;
    private Pay pay;
}
