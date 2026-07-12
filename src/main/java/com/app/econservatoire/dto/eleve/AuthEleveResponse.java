package com.app.econservatoire.dto.eleve;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.app.econservatoire.models.Pay;
import com.app.econservatoire.models.enums.Sexe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthEleveResponse {

    private Integer id;

    private String nom;
    private String prenom;

    private String nom_ar;
    private String prenom_ar;

    private Sexe sexe;

    private LocalDate date_naissance;

    private String lieu_naissance;

    private Pay pay;

    private String cin;

    private String adresse;

    private String mobile;

    private String fixe;

    private String email;

    private String nom_pere;

    private String profession_pere;

    private String parent_culture;

    private String drpp_parent;

    private String lien_parente;

    private String identifiantUnique;

    private boolean enabled;

    private boolean valide;

    private String profile;

    private String photo;

    private boolean nouveau;

    private LocalDateTime created_at;
}