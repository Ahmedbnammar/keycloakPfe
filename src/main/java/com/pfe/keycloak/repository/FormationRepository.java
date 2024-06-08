package com.pfe.keycloak.repository;

import com.pfe.keycloak.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    Optional<Formation> findByCode(String code);


}
