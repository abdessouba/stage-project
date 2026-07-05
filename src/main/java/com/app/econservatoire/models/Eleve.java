package com.app.econservatoire.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.app.econservatoire.models.enums.Sexe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "eleves", indexes = {
    @Index(name = "FK_eleve_pays_id_pays", columnList = "pay_id"),
})
public class Eleve {

    // ==========================================================
    // Primary Key
    // ==========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ==========================================================
    // Personal Information
    // ==========================================================

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(length = 100)
    private String nom_ar;

    @Column(length = 100)
    private String prenom_ar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Sexe sexe;

    @Column(nullable = false)
    private LocalDate date_naissance;

    @Column(length = 100)
    private String lieu_naissance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_id", nullable = false)
    private Pay pay;

    @Column(length = 20)
    private String cin;

    // ==========================================================
    // Contact Information
    // ==========================================================

    @Column(length = 255)
    private String adresse;

    @Column(length = 15)
    private String mobile;

    @Column(length = 15)
    private String fixe;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // ==========================================================
    // Parent Information
    // ==========================================================

    @Column(length = 100)
    private String nom_pere;

    @Column(length = 100)
    private String profession_pere;

    @Column(length = 50)
    private String parent_culture;

    @Column(length = 10)
    private String drpp_parent;

    @Column(length = 10)
    private String lien_parente;

    // ==========================================================
    // Account Information
    // ==========================================================

    @Column(name = "identifiant_unique", unique = true, updatable = false, length = 100)
    private String identifiantUnique;

    @Column(nullable = false, length = 255)
    private String password;

    private boolean enabled = false;

    private boolean valide = false;

    @Column(length = 100)
    private String profile;

    @Column(length = 100)
    private String photo;

    private boolean nouveau;

    // ==========================================================
    // Administrative Information
    // ==========================================================

    @Column(nullable = false)
    private boolean lu_condition;

    private boolean militaire;

    private boolean reclassement;

    @Column(length = 50)
    private String tarif;

    private boolean frais_imprime;

    // ==========================================================
    // Audit Information
    // ==========================================================

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated_at;

    // ==========================================================
    // Relationships
    // ==========================================================
    @OneToMany(mappedBy = "eleve")
    private List<Journalisation> journalisations;
}