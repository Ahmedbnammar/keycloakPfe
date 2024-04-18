package com.pfe.keycloak.service;




import com.pfe.keycloak.dto.CertificationDto;
import com.pfe.keycloak.model.Certification;

import java.util.List;

public interface CertificationService {
        Certification createCertification(CertificationDto certification, Long employeeId);
        Certification getCertificationById(Long id, Long employeeId);
        List<Certification> getAllCertifications(Long employeeId);
        Certification updateCertification(CertificationDto certification, Long employeeId);
        void deleteCertification(Long id, Long employeeId);
    }


