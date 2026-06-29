package com.app.econservatoire.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.econservatoire.models.Eleve;

public interface EleveRepository extends JpaRepository<Eleve, Integer> {
    public Boolean existsByEmail(String email);
    public Optional<Eleve> findByEmail(String email);
    
    public Optional<Eleve> findByEmailOrIdentifiantUnique(String email, String identifier_unique);
}
