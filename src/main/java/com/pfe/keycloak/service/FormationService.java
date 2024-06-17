package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.FormationDto;
import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.Formation;
import com.pfe.keycloak.model.PlanDeDeveloppement;

public interface FormationService {


    public Formation addFormation(Long id, FormationDto formation); ;

    public void removeFormation( Long id);

    public Formation updateFormation( FormationDto formation);

    public Formation findFormation(Long formation);

    public Iterable<Formation> findAllFormation(Long id);



}
