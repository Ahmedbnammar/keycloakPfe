package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.FormationDto;
import com.pfe.keycloak.model.Formation;

public interface FormationService {


    public Formation addFormation(String matricule, FormationDto formation);

    public void removeFormation(String matricule, Long id);

    public Formation updateFormation( FormationDto formation);

    public Formation findFormation(Long formation);

    public Iterable<Formation> findAllFormation(Long id);



}
