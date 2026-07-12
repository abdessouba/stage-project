package com.app.econservatoire.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.econservatoire.Repository.EleveRepository;
import com.app.econservatoire.Repository.PayRepository;
import com.app.econservatoire.dto.eleve.AuthEleveResponse;
import com.app.econservatoire.dto.eleve.EleveRegistrationRequest;
import com.app.econservatoire.dto.eleve.EleveSignInReponse;
import com.app.econservatoire.dto.eleve.EleveSignInRequest;
import com.app.econservatoire.exceptions.eleve.AccountNotVerifiedException;
import com.app.econservatoire.exceptions.eleve.EleveAuthenticationException;
import com.app.econservatoire.exceptions.pay.PayNotFoundException;
import com.app.econservatoire.mapper.EleveMapper;
import com.app.econservatoire.models.Eleve;
import com.app.econservatoire.models.Pay;
import com.app.econservatoire.security.UserIdGenerator;
import com.app.econservatoire.security.jwt.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EleveAuthService {

    private final EleveMapper eleveMapper;
    private final EleveRepository eleveRepository;
    private final PayRepository payRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenVerifyService tokenVerifyService;
    private final JwtService jwtService;


    public void registerEleve(EleveRegistrationRequest requestEleve) {

        if (eleveRepository.existsByEmail(requestEleve.getEmail())) {
            throw new EleveAuthenticationException("Un compte associé à cette adresse e-mail existe déjà.");
        }

        Pay pay = payRepository.findById(requestEleve.getPayId())
        .orElseThrow(()-> new PayNotFoundException("Pay introuvable."));
            
        Eleve eleve = eleveMapper.toEntity(requestEleve);
        
        eleve.setPassword(passwordEncoder.encode(requestEleve.getPassword()));
        eleve.setPay(pay);
        eleve.setIdentifiantUnique(UserIdGenerator.generate());

        Eleve savedEleve = eleveRepository.save(eleve);

        tokenVerifyService.sendVerificationEmail(savedEleve);
    }
    
    public EleveSignInReponse signInEleve(EleveSignInRequest request, HttpServletResponse response){
        Eleve eleve = eleveRepository
            .findByEmailOrIdentifiantUnique(request.getIdentifier(), request.getIdentifier())
            .orElseThrow(() -> new EleveAuthenticationException("Identifiant incorrect."));

        if (!passwordEncoder.matches(request.getPassword(), eleve.getPassword())) {
            throw new EleveAuthenticationException("Le mot de passe est incorrect.");
        }

        if(!eleve.isValide()){
            throw new AccountNotVerifiedException("Votre compte n'est pas encore vérifié. Veuillez vérifier votre boîte de réception.");
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

    @Transactional(readOnly = true)
    public AuthEleveResponse getAuthenticatedEleve(HttpServletRequest request) {
        String token = extractJwtToken(request);

        if (token == null || token.isBlank()) {
            throw new EleveAuthenticationException("Token manquant.");
        }

        try {
            Claims jsonClaims = jwtService.extractJsonData(token);
            String email = jsonClaims.getSubject();

            Eleve eleve = eleveRepository
                .findByEmail(email)
                .orElseThrow(() -> new EleveAuthenticationException("Utilisateur introuvable."));

            return eleveMapper.toAuthResponse(eleve);
        } catch (RuntimeException exception) {
            throw new EleveAuthenticationException("Token invalide.");
        }
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private String extractJwtToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}