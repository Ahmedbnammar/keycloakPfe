package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.CompetenceDto;
import com.pfe.keycloak.model.Competences;
import com.pfe.keycloak.service.CompetencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competences")
public class CompetencesController {

    @Autowired
    private CompetencesService competencesService;

    @PostMapping
    public ResponseEntity<Competences> createCompetence(@RequestBody CompetenceDto competence) {
        return ResponseEntity.ok(competencesService.createCompetence(competence));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competences> getCompetenceById(@PathVariable Long id) {
        return ResponseEntity.ok(competencesService.getCompetenceById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Competences>> getAllCompetences() {
        return ResponseEntity.ok(competencesService.getAllCompetences());
    }

   /* @PutMapping
    public ResponseEntity<Competences> updateCompetence(@RequestBody Competences competence) {
        return ResponseEntity.ok(competencesService.updateCompetence(competence));
    }*/

    @PutMapping("/update/{id}")
    public ResponseEntity<Competences> updateCompetence(@PathVariable Long id, @RequestBody Competences newCompetenceData) {
        Competences existingCompetence = competencesService.getCompetenceById(id);
        if (existingCompetence == null) {
            return ResponseEntity.notFound().build();
        }
        existingCompetence.setCode(newCompetenceData.getCode());
        existingCompetence.setNom(newCompetenceData.getNom());
        existingCompetence.setDescription(newCompetenceData.getDescription());
        existingCompetence.setLevel(newCompetenceData.getLevel());

        Competences updatedCompetence = competencesService.updateCompetence(existingCompetence);

        return ResponseEntity.ok(updatedCompetence);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        competencesService.deleteCompetence(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{competenceId}/employee/{employeeId}")
    public ResponseEntity<Competences> addEmployeeToCompetence(@PathVariable Long competenceId,
                                                               @PathVariable Long employeeId) {
        return ResponseEntity.ok(competencesService.addEmployeeToCompetence(competenceId, employeeId));
    }

    @DeleteMapping("/{competenceId}/employee/{employeeId}")
    public ResponseEntity<Competences> removeEmployeeFromCompetence(@PathVariable Long competenceId,
                                                                    @PathVariable Long employeeId) {
        return ResponseEntity.ok(competencesService.removeEmployeeFromCompetence(competenceId, employeeId));
    }
}
