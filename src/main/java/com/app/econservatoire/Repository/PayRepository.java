package com.app.econservatoire.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.econservatoire.models.Pay;

public interface PayRepository extends JpaRepository<Pay, Integer>{
    
}
