package com.app.econservatoire.dto.eleve;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EleveRequest {

    @NotBlank
    @Size(max = 100)
    private String nom;

    @NotBlank
    @Size(max = 100)
    private String prenom;

    @Size(max = 100)
    private String nom_ar;

    @NotBlank
    private String sexe;

    @NotNull
    @Past
    private LocalDate date_naissance;

    @Size(max = 50)
    private String lieu_naissance;

    @NotNull
    private Integer payId;

    @Size(max = 200)
    private String adresse;

    @Size(max = 20)
    private String cin;

    @Size(max = 100)
    private String nom_pere;

    @Size(max = 100)
    private String profession_pere;

    @Size(max = 15)
    private String mobile;

    @Size(max = 15)
    private String fixe;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String identifiant_unique;

    @Size(max = 200)
    private String nom_prenom_ar;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    @Size(max = 100)
    private String photo;

    @NotBlank
    @Size(max = 100)
    private String profile;

    private boolean lu_condition;

    private boolean militaire;

    private boolean reclassement;

    @Size(max = 50)
    private String tarif;

    @Size(max = 50)
    private String parent_culture;

    @Size(max = 10)
    private String drpp_parent;

    @Size(max = 10)
    private String lien_parente;

    private boolean frais_imprime;
}
