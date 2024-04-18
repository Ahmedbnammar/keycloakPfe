package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.ExperienceDto;
import com.pfe.keycloak.model.Experience;

import java.util.List;

public interface ExperienceService {
    Experience addExperience(String matricule, ExperienceDto experience);
    void removeExperience(String matricule, String code);
    Experience updateExperience(String matricule, ExperienceDto experience);
    Experience findByCode(String code);
    List<Experience> findAll(String matricule );

}
