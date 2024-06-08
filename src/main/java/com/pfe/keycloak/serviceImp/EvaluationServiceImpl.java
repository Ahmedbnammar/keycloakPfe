package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.dto.EvaluationDto;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.model.Evaluation;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.repository.EvaluationRepository;
import com.pfe.keycloak.service.EvaluationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Logger;

@Service
public class EvaluationServiceImpl implements EvaluationService {


    public Evaluation saveEvaluation(Evaluation evaluation) {
        assertCodeUnique(evaluation.getCode());
        return evaluationRepository.save(evaluation);
    }

    private void assertCodeUnique(String code) {
        if (code != null && evaluationRepository.findByCode(code).isPresent()) {
            Logger.getLogger("Code already exists");
        }
    }

    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Evaluation createEvaluation(EvaluationDto evaluation, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Evaluation evaluation1 = new Evaluation();
        evaluation1.setCode(evaluation.getCode());
        evaluation1.setDescription(evaluation.getDescription());
        evaluation1.setResultat(evaluation.getResultat());
        evaluation1.setDateEvaluation(evaluation.getDateEvaluation());
        evaluation1.setEmployee(employee);
        employee.addEvaluation(evaluation1);
        return saveEvaluation(evaluation1);
    }

    @Override
    public Evaluation getEvaluationById(Long id,Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return employee.findEvaluationById(id);
    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public Evaluation updateEvaluation(EvaluationDto evaluation, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Evaluation existingEvaluation = employee.findEvaluationByCode(evaluation.getCode());
        existingEvaluation.setDateEvaluation(evaluation.getDateEvaluation());
        existingEvaluation.setDescription(evaluation.getDescription());
        existingEvaluation.setResultat(evaluation.getResultat());
        return evaluationRepository.save(existingEvaluation);
    }

    @Override
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}
