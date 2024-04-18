package com.pfe.keycloak.repository;

import com.pfe.keycloak.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    Optional<Certification> findByCode(String code);



}
