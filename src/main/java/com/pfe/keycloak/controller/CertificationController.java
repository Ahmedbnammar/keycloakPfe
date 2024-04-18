package com.pfe.keycloak.controller;


import com.pfe.keycloak.dto.CertificationDto;
import com.pfe.keycloak.model.Certification;
import com.pfe.keycloak.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{employeeId}/certifications")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    @PostMapping
    public ResponseEntity<Certification> createCertification(@RequestBody CertificationDto certification,
                                                             @PathVariable Long employeeId) {
        return ResponseEntity.ok(certificationService.createCertification(certification, employeeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certification> getCertificationById(@PathVariable Long id,@PathVariable Long employeeId) {
        return ResponseEntity.ok(certificationService.getCertificationById(id, employeeId));
    }

    @GetMapping
    public ResponseEntity<List<Certification>> getAllCertifications(@PathVariable Long employeeId) {
        return ResponseEntity.ok(certificationService.getAllCertifications(employeeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certification> updateCertification(@RequestBody CertificationDto certification, @PathVariable Long id,
                                                             @PathVariable Long employeeId) {
        return ResponseEntity.ok(certificationService.updateCertification(certification, employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id,@PathVariable Long employeeId) {
        certificationService.deleteCertification(id, employeeId);
        return ResponseEntity.ok().build();
    }
}
