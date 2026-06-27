package com.app.econservatoire.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Index(name = "FK_eleve_pays_id_pays", columnList = "pay"),
})
public class Eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(length = 100)
    private String nom_ar;

    @Column(length = 10)
    private String sexe;

    private LocalDate date_naissance;

    @Column(length = 50)
    private String lieu_naissance;

    @ManyToOne
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @Column(length = 200)
    private String adresse;

    @Column(length = 20)
    private String cin;

    @Column(length = 100)
    private String nom_pere;

    @Column(length = 100)
    private String profession_pere;

    @Column(length = 15)
    private String mobile;

    @Column(length = 15)
    private String fixe;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String identifiant_unique;

    @Column(length = 200)
    private String nom_prenom_ar;
    
    @Column(length = 255)
    private String password;

    private Boolean enabled;

    @Column(length = 100)
    private String photo;

    private Boolean valide;

    @Column(nullable = false, length = 100)
    private String profile;

    private Boolean nouveau;

    @Column(nullable = false)
    private boolean lu_condition;

    @Column(nullable = false)
    private boolean militaire;

    @Column(nullable = false)
    private boolean reclassement;

    @Column(length = 50)
    private String tarif;

    @Column(length = 50)
    private String parent_culture;

    @Column(length = 10)
    private String drpp_parent;

    @Column(length = 10)
    private String lien_parente;

    @Column(nullable = false)
    private boolean frais_imprime;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated_at;

    // relationship with verificationToken table
    @OneToMany(mappedBy = "eleve")
    private List<VerificationToken> tokens;

    // relationship with resetPassword table
    @OneToMany(mappedBy = "eleve")
    private List<ResetPassword> resetPasswords;

    // relationship with journalisations table
    @OneToMany(mappedBy = "eleve")
    private List<Journalisation> journalisations;

    public boolean isAccountVerified(){
        return this.enabled;
    }
}
