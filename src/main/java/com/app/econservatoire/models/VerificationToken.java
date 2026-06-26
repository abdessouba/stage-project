package com.app.econservatoire.models;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "verificationtoken", indexes = {
    @Index(name = "FK_verificationtoken_compte", columnList = "eleve")
})
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "expiry_date")
    private LocalDate expirydate;

    @Column(length = 255)
    private String token;

    private boolean verified = false;

    @ManyToOne
    @JoinColumn(name = "eleve", nullable = false)
    private Eleve eleve;
}
