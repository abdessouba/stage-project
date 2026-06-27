package com.app.econservatoire.mapper;


import org.mapstruct.Mapper;

import com.app.econservatoire.dto.eleve.EleveRequest;
import com.app.econservatoire.dto.eleve.EleveResponse;
import com.app.econservatoire.models.Eleve;


@Mapper(componentModel = "spring")
public interface EleveMapper {

    Eleve toEntity(EleveRequest request);
    EleveResponse toEntityResponse(Eleve request);

}