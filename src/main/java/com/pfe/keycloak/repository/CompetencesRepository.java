package com.pfe.keycloak.repository;

import com.pfe.keycloak.model.Competences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencesRepository extends JpaRepository<Competences, Long> {
}
