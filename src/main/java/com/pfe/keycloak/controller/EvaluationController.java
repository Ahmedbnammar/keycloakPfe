package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.EvaluationDto;
import com.pfe.keycloak.model.Evaluation;
import com.pfe.keycloak.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{employeeId}/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody EvaluationDto evaluation,
                                                       @PathVariable Long employeeId) {
        return ResponseEntity.ok(evaluationService.createEvaluation(evaluation, employeeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long id,@PathVariable Long employeeId) {
        return ResponseEntity.ok(evaluationService.getEvaluationById(id,employeeId));
    }

    @GetMapping
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        return ResponseEntity.ok(evaluationService.getAllEvaluations());
    }

    @PutMapping
    public ResponseEntity<Evaluation> updateEvaluation(@RequestBody EvaluationDto evaluation,
                                                       @PathVariable Long employeeId) {
        return ResponseEntity.ok(evaluationService.updateEvaluation(evaluation, employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.ok().build();
    }
}
