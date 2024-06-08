package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.ExperienceDto;
import com.pfe.keycloak.model.Experience;

import java.util.List;

public interface ExperienceService {
    Experience addExperience(long id, ExperienceDto experience);
    void removeExperience( long code);
    Experience updateExperience(long id, ExperienceDto experience);
    Experience findByCode(long id,String code);
    List<Experience> findAll(long id );

}
