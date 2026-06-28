package com.app.econservatoire.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.econservatoire.models.ResetPassword;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Integer> {
    public Optional<ResetPassword> findByToken(String token);
}
