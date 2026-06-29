package com.app.econservatoire.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.econservatoire.Repository.EleveRepository;
import com.app.econservatoire.Repository.PayRepository;
import com.app.econservatoire.dto.eleve.EleveRequest;
import com.app.econservatoire.dto.eleve.EleveSignInReponse;
import com.app.econservatoire.dto.eleve.EleveSignInRequest;
import com.app.econservatoire.mapper.EleveMapper;
import com.app.econservatoire.models.Eleve;
import com.app.econservatoire.models.Pay;
import com.app.econservatoire.security.jwt.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EleveService {

    private final EleveMapper eleveMapper;
    private final EleveRepository eleveRepository;
    private final PayRepository payRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenVerifyService tokenVerifyService;
    private final JwtService jwtService;


    public void registerEleve(EleveRequest requestEleve) {

        if (eleveRepository.existsByEmail(requestEleve.getEmail())) {
            throw new RuntimeException("User With This Email already exists");
        }

        Pay pay = payRepository.findById(requestEleve.getPayId())
        .orElseThrow(()-> new RuntimeException("Pay not found."));
            
        Eleve eleve = eleveMapper.toEntity(requestEleve);
        
        eleve.setPassword(passwordEncoder.encode(requestEleve.getPassword()));
        eleve.setPay(pay);

        Eleve savedEleve = eleveRepository.save(eleve);

        tokenVerifyService.verifyEmail(savedEleve);
    }
    
    public EleveSignInReponse signInUser(EleveSignInRequest request, HttpServletResponse response){
        Eleve eleve = eleveRepository
            .findByEmailOrIdentifiantUnique(request.getIdentifier(), request.getIdentifier())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), eleve.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(eleve.getId(), eleve.getEmail());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");      
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        Claims jsonClaims = jwtService.extractJsonData(token);

        EleveSignInReponse eleveReponse = new EleveSignInReponse();
        eleveReponse.setEmail(jsonClaims.getSubject());
        eleveReponse.setId(jsonClaims.get("id", Integer.class));

        return eleveReponse;
    }
}