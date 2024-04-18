package com.pfe.keycloak.service;

import com.pfe.keycloak.model.Competences;

import java.util.List;
public interface CompetencesService {
    Competences createCompetence(Competences competence);
    Competences getCompetenceById(Long id);
    List<Competences> getAllCompetences();
    Competences updateCompetence(Competences competence);
    void deleteCompetence(Long id);
    Competences addEmployeeToCompetence(Long competenceId, Long employeeId);
    Competences removeEmployeeFromCompetence(Long competenceId, Long employeeId);
}
