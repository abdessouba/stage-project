package com.app.econservatoire.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.econservatoire.models.Eleve;

public interface EleveRepository extends JpaRepository<Eleve, Integer> {
    public Boolean existsByEmail(String email);
}
