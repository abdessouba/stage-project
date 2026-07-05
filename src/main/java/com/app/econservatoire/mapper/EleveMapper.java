package com.app.econservatoire.mapper;


import org.mapstruct.Mapper;

import com.app.econservatoire.dto.eleve.EleveRegistrationRequest;
import com.app.econservatoire.dto.eleve.EleveResponse;
import com.app.econservatoire.models.Eleve;


@Mapper(componentModel = "spring")
public interface EleveMapper {

    Eleve toEntity(EleveRegistrationRequest request);
    EleveResponse toEntityResponse(Eleve request);

}