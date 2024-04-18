package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.dto.CertificationDto;
import com.pfe.keycloak.model.Certification;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.repository.CertificationRepository;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.service.CertificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Logger;

@Service
public class CertificationServiceImpl implements CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    public Certification saveCertification(Certification certification) {
        assertCodeUnique(certification.getCode());
        return certificationRepository.save(certification);
    }

    private void assertCodeUnique(String code) {
        if (code != null && certificationRepository.findByCode(code).isPresent()) {
            Logger.getLogger("Code already exists");
        }
    }
    @Override
    public Certification createCertification(CertificationDto certification, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Certification certification1 = new Certification();
        certification1.setCode(certification.getCode());
        certification1.setDescription(certification.getDescription());
        certification1.setDateAcquisition(certification.getDateAcquisition());
        certification1.setEmployee(employee);
        employee.addCertification(certification1);
        certification1.setEmployee(employee);
        return certificationRepository.save(certification1);
    }

    @Override
    public Certification getCertificationById(Long id, Long employeeId) {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return employee.findCertificationById(id);



    }

    @Override
    public List<Certification> getAllCertifications( Long employeeId) {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return employee.getCertifications();
    }

    @Override
    public Certification updateCertification(CertificationDto certification, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Certification existingCertification = employee.findCertificationByCode(certification.getCode());
        existingCertification.setDateAcquisition(certification.getDateAcquisition());
        existingCertification.setDescription(certification.getDescription());
        existingCertification.setTitre(certification.getTitre());
        return certificationRepository.save(existingCertification);

    }

    @Override
    public void deleteCertification(Long id, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employee.removeCertificationById(id);
        certificationRepository.deleteById(id);
    }
}
