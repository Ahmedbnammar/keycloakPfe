package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.model.PlanDeDeveloppement;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.repository.PlanDeDeveloppementRepository;
import com.pfe.keycloak.service.PlanDeDeveloppementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanDeDeveloppementServiceImpl implements PlanDeDeveloppementService {
    @Autowired
    public PlanDeDeveloppementRepository planDeDeveloppementRepository;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Override
    public PlanDeDeveloppement addPlanDeDeveloppement(String matricule, PlanDeDeveloppementDto planDeDeveloppement) {
        Employee employee = employeeRepository.findByMatricule(matricule).orElseThrow(() ->
                new RuntimeException("Employee not found for matricule: \"" + matricule + "\"")
        );
        PlanDeDeveloppement planDeDeveloppement1 = new PlanDeDeveloppement();
        planDeDeveloppement1.setActions(planDeDeveloppement.getActions());
        planDeDeveloppement1.setCode(planDeDeveloppement.getCode());
        planDeDeveloppement1.setDateFin(planDeDeveloppement.getDateFin());
        planDeDeveloppement1.setDateDebut(planDeDeveloppement.getDateDebut());
        planDeDeveloppement1.setEmployee(employee);
        employee.addPlanDeDeveloppement(planDeDeveloppement1);
        return planDeDeveloppementRepository.save(planDeDeveloppement1);

    }

    @Override
    public PlanDeDeveloppement updatePlanDeDeveloppement(String matricule, PlanDeDeveloppementDto planDeDeveloppement) {
        Employee employee = employeeRepository.findByMatricule(matricule).orElseThrow(() ->
                new RuntimeException("Employee not found for matricule: \"" + matricule + "\"")
        );
        PlanDeDeveloppement planDeDeveloppement1 = employee.findPlanDeDeveloppementByCode(planDeDeveloppement.getCode());
        planDeDeveloppement1.setActions(planDeDeveloppement.getActions());
        planDeDeveloppement1.setCode(planDeDeveloppement.getCode());
        planDeDeveloppement1.setDateFin(planDeDeveloppement.getDateFin());
        planDeDeveloppement1.setDateDebut(planDeDeveloppement.getDateDebut());
        return planDeDeveloppementRepository.save(planDeDeveloppement1);
    }

    @Override
    public void removePlanDeDeveloppement( Long id) {
        planDeDeveloppementRepository.deleteById(id);


    }

    @Override
    public PlanDeDeveloppement findPlanDeDeveloppement(Long id) {
        return planDeDeveloppementRepository.findById(id).orElseThrow(() -> new RuntimeException("Plan de developpement not found"));
    }

    @Override
    public Iterable<PlanDeDeveloppement> findAllPlanDeDeveloppement(String matricule) {
        Employee employee = employeeRepository.findByMatricule(matricule).orElseThrow(() ->
                new RuntimeException("Employee not found for matricule: \"" + matricule + "\"")
        );
        return employee.getPlanDeDeveloppements();
    }
}
