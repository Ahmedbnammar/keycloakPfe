package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.dto.EmployeeDto;
import com.pfe.keycloak.model.*;
import com.pfe.keycloak.repository.*;
import com.pfe.keycloak.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public CertificationRepository certificationRepository;
    @Autowired
    public CompetencesRepository competencesRepository;
    @Autowired
    public EvaluationRepository evaluationRepository;
    @Autowired
    public ExperienceRepository expericeRepository;
    @Autowired
    public FormationRepository formationRepository;
    @Autowired
    public PlanDeDeveloppementRepository planDeDeveloppementRepository;


    @Override
    public Employee register(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee addFormation(String matricule, Formation formation) {
            Optional<Employee> optionalEmployee = employeeRepository.findByMatricule(matricule);

            Employee employee = optionalEmployee.orElseThrow(() ->
                    new ExpressionException("Employee not found for matricule: \"" + matricule + "\"")
            );
            Formation formation1 = new Formation();
            formation1.setCode(formation.getCode());
            formation1.setTitre(formation.getTitre());
            formation1.setDescription(formation.getDescription());
            formation1.setDateDebut(formation.getDateDebut());
            formation1.setDateFin(formation.getDateFin());
            employee.addFormation(formation1);
            formation1.setEmployee(employee);
            formationRepository.save(formation1);

            return employeeRepository.save(employee);

    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);

    }

    @Override
    public Employee updateEmployee(Long employeeId, EmployeeDto employee) {
        Employee employee1 = employeeRepository.findById(employeeId).get();
        employee1.setNom(employee.getNom());
        employee1.setPrenom(employee.getPrenom());
        employee1.setEmail(employee.getEmail());
        employee1.setStatus(employee.getStatus());
        employee1.setSexe(employee.getSexe());
        employee1.setDateNaissance(employee.getDateNaissance());
        employee1.setTelephone(employee.getTelephone());
        employee1.setUserName(employee.getUserName());
        return employeeRepository.save(employee1);
    }

    public Employee addCertification(Long id, Certification certification) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            assert employee.getCertifications() != null;
            employee.getCertifications().add(certification);
            certification.setEmployee(employee);
            certificationRepository.save(certification);
            return employeeRepository.save(employee);
        }
        catch (Exception e){
            log.error("Error while saving certification", e);
        }
        return null;
    }
    public Employee addExperience(Long id,  Experience experience) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            assert employee.getExperiences() != null;
            employee.getExperiences().add(experience);
            experience.setEmployee(employee);
            expericeRepository.save(experience);
            return employeeRepository.save(employee);
        }
        catch (Exception e){
            log.error("Error while saving experience", e);
        }
        return null;
    }
    public Employee addEvaluation(Long id, Evaluation evaluation) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            assert employee.getEvaluations() != null;
            employee.getEvaluations().add(evaluation);
            evaluation.setEmployee(employee);
            evaluationRepository.save(evaluation);
            return employeeRepository.save(employee);
        }
        catch (Exception e){
            log.error("Error while saving evaluation", e);
        }
        return null;
    }
    public Employee addPlanDeDeveloppement(Long id,  PlanDeDeveloppement planDeDeveloppement) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            assert employee.getPlanDeDeveloppements() != null;
            employee.getPlanDeDeveloppements().add(planDeDeveloppement);
            planDeDeveloppement.setEmployee(employee);
            planDeDeveloppementRepository.save(planDeDeveloppement);
            return employeeRepository.save(employee);
        }
        catch (Exception e){
            log.error("Error while saving plan de developpement", e);
        }
        return null;
    }
    public Employee addCompetence(Long id,  Competences competences) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            assert employee.getCompetences() != null;
            employee.getCompetences().add(competences);
            competences.getEmployees().add(employee);
            competencesRepository.save(competences);
            return employeeRepository.save(employee);
        }
        catch (Exception e){
            log.error("Error while saving competence", e);
        }
        return null;
    }
    @Override
    public void remove(Long id) {
        employeeRepository.deleteById(id);

    }

    @Override
    public Employee update(Employee employee) {
        employeeRepository.findById(employee.getId()).get();
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public Employee findByMatricule(String matricule) {
        return (Employee) employeeRepository.findByMatricule(matricule).get();
    }
}