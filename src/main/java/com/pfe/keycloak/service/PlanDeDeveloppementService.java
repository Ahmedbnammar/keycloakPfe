package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.PlanDeDeveloppement;

public interface PlanDeDeveloppementService {
    public PlanDeDeveloppement addPlanDeDeveloppement(String matricule, PlanDeDeveloppementDto planDeDeveloppement); ;
    public PlanDeDeveloppement updatePlanDeDeveloppement(String matricule, PlanDeDeveloppementDto planDeDeveloppement);
    public void removePlanDeDeveloppement( Long id);
    public PlanDeDeveloppement findPlanDeDeveloppement(Long id);
    public Iterable<PlanDeDeveloppement> findAllPlanDeDeveloppement(String matricule);
}
