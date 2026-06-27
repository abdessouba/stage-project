package com.app.econservatoire.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.econservatoire.Repository.EleveRepository;
import com.app.econservatoire.Repository.PayRepository;
import com.app.econservatoire.dto.eleve.EleveRequest;
import com.app.econservatoire.mapper.EleveMapper;
import com.app.econservatoire.models.Eleve;
import com.app.econservatoire.models.Pay;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EleveService {

    private final EleveMapper eleveMapper;
    private final EleveRepository eleveRepository;
    private final PayRepository payRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService emailVerificationService;


    public void registerEleve(EleveRequest requestEleve) {

        if (eleveRepository.existsByEmail(requestEleve.getEmail())) {
            throw new RuntimeException("User With This Email already exists");
        }

        Pay pay = payRepository.findById(requestEleve.getPayId()).orElseThrow(()-> new RuntimeException("Pay not found."));
            
        requestEleve.setPassword(passwordEncoder.encode(requestEleve.getPassword()));

        Eleve eleve = eleveMapper.toEntity(requestEleve);

        eleve.setPay(pay);

        Eleve savedEleve = eleveRepository.save(eleve);

        // handle email verification after eleve created
        emailVerificationService.handleVerificationEmail(savedEleve);
    }
}