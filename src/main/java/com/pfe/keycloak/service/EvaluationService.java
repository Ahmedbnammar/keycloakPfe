package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.EvaluationDto;
import com.pfe.keycloak.model.Evaluation;

import java.util.List;

public interface EvaluationService {
    Evaluation createEvaluation(EvaluationDto evaluation, Long employeeId);
    Evaluation getEvaluationById(Long id,Long employeeId);
    List<Evaluation> getAllEvaluations();
    Evaluation updateEvaluation(EvaluationDto evaluation, Long employeeId);
    void deleteEvaluation(Long id);
}
