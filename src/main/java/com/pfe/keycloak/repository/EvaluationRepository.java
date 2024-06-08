package com.pfe.keycloak.repository;

import com.pfe.keycloak.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
        java.util.Optional<Evaluation> findByCode(String code);

}
