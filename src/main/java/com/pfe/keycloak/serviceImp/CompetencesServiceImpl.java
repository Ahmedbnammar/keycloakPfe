package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.model.Competences;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.repository.CompetencesRepository;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.service.CompetencesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CompetencesServiceImpl implements CompetencesService {

    @Autowired
    private CompetencesRepository competencesRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Competences createCompetence(Competences competence) {
        // Logic to save the competence
        return competencesRepository.save(competence);
    }

    @Override
    public Competences getCompetenceById(Long id) {
        // Logic to fetch a competence by id
        return competencesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competence not found"));
    }

    @Override
    public List<Competences> getAllCompetences() {
        // Logic to fetch all competences
        return competencesRepository.findAll();
    }

    @Override
    public Competences updateCompetence(Competences competence) {
        // Logic to update a competence
        Competences existingCompetence = getCompetenceById(competence.getId());
        // Update fields of existingCompetence as needed
        return competencesRepository.save(existingCompetence);
    }

    @Override
    public void deleteCompetence(Long id) {
        // Logic to delete a competence
        competencesRepository.deleteById(id);
    }

    @Override
    public Competences addEmployeeToCompetence(Long competenceId, Long employeeId) {
        Competences competence = getCompetenceById(competenceId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        competence.getEmployees().add(employee);
        return competencesRepository.save(competence);
    }

    @Override
    public Competences removeEmployeeFromCompetence(Long competenceId, Long employeeId) {
        Competences competence = getCompetenceById(competenceId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        competence.getEmployees().remove(employee);
        return competencesRepository.save(competence);
    }
}
