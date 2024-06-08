package com.pfe.keycloak.repository;

import com.pfe.keycloak.model.PlanDeDeveloppement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanDeDeveloppementRepository extends JpaRepository<PlanDeDeveloppement, Long> {
}
