package com.app.econservatoire.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter 
@Entity
@Table(name = "reset_password", indexes = {
    @Index(name="eleve", columnList = "eleve")
})
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "eleve",nullable = false)
    private Eleve eleve;

    @Column(length = 100, nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime date_expiration;

    @Column(nullable = false)
    private boolean used = false;
}