package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.PlanDeDeveloppement;

public interface PlanDeDeveloppementService {
    public PlanDeDeveloppement addPlanDeDeveloppement(Long id, PlanDeDeveloppementDto planDeDeveloppement); ;
    public PlanDeDeveloppement updatePlanDeDeveloppement(Long id, PlanDeDeveloppementDto planDeDeveloppement);
    public void removePlanDeDeveloppement( Long id);
    public PlanDeDeveloppement findPlanDeDeveloppement(Long id);
    public Iterable<PlanDeDeveloppement> findAllPlanDeDeveloppement(Long id);
}
