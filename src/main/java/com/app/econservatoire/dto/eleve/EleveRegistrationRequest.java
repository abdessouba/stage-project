package com.app.econservatoire.dto.eleve;

import java.time.LocalDate;

import com.app.econservatoire.models.enums.Sexe;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EleveRegistrationRequest {

    // ==========================================================
    // Personal Information
    // ==========================================================

    @NotBlank(message = "Le nom est obligatoire.")
    @Size(min = 2, max = 100)
    @Pattern(
        regexp = "^[A-Za-zÀ-ÿ' -]+$",
        message = "Le nom contient des caractères invalides."
    )
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire.")
    @Size(min = 2, max = 100)
    @Pattern(
        regexp = "^[A-Za-zÀ-ÿ' -]+$",
        message = "Le prénom contient des caractères invalides."
    )
    private String prenom;

    @Size(max = 100)
    @Pattern(
        regexp = "^[\\p{IsArabic}\\s]+$",
        message = "Le nom en arabe doit contenir uniquement des caractères arabes."
    )
    private String nom_ar;

    @Size(max = 100)
    @Pattern(
        regexp = "^[\\p{IsArabic}\\s]+$",
        message = "Le prénom en arabe doit contenir uniquement des caractères arabes."
    )
    private String prenom_ar;

    @NotNull(message = "Le sexe est obligatoire.")
    private Sexe sexe;

    @NotNull(message = "La date de naissance est obligatoire.")
    @Past(message = "La date de naissance doit être antérieure à la date actuelle.")
    private LocalDate date_naissance;

    @Size(max = 100)
    @Pattern(
        regexp = "^[A-Za-zÀ-ÿ' -]+$",
        message = "Le lieu de naissance contient des caractères invalides."
    )
    private String lieu_naissance;

    @NotNull(message = "Le pays est obligatoire.")
    private Integer payId;

    @Size(max = 20)
    @Pattern(
        regexp = "^[A-Z]{1,2}[0-9]{5,6}$",
        message = "Le format du CIN est invalide."
    )
    private String cin;

    // ==========================================================
    // Contact Information
    // ==========================================================

    @Size(max = 255)
    private String adresse;

    @Pattern(
        regexp = "^(\\+212|0)(5|6|7)[0-9]{8}$",
        message = "Le numéro de téléphone mobile est invalide."
    )
    private String mobile;

    @Pattern(
        regexp = "^(\\+212|0)5[0-9]{8}$",
        message = "Le numéro de téléphone fixe est invalide."
    )
    private String fixe;

    @NotBlank(message = "L'adresse e-mail est obligatoire.")
    @Email(message = "L'adresse e-mail est invalide.")
    @Size(max = 100)
    private String email;

    // ==========================================================
    // Parent Information
    // ==========================================================

    @Size(max = 100)
    @Pattern(
        regexp = "^[A-Za-zÀ-ÿ' -]+$",
        message = "Le nom du père contient des caractères invalides."
    )
    private String nom_pere;

    @Size(max = 100)
    private String profession_pere;

    @Size(max = 50)
    private String parent_culture;

    @Size(max = 10)
    private String drpp_parent;

    @Size(max = 10)
    private String lien_parente;

    // ==========================================================
    // Account
    // ==========================================================

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 8, max = 255)
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
        message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial."
    )
    private String password;

    @AssertTrue(message = "Vous devez accepter les conditions d'utilisation.")
    private boolean lu_condition;

    // ==========================================================
    // Administrative
    // ==========================================================

    private boolean militaire;

    private boolean reclassement;

    @Size(max = 50)
    private String tarif;
}