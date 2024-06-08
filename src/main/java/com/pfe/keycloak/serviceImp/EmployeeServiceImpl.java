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
    private EmployeeRepository employeeRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private CompetencesRepository competencesRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private PlanDeDeveloppementRepository planDeDeveloppementRepository;

    @Override
    public Employee register(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public Employee addFormation(String matricule, Formation formation) {
        Optional<Employee> optionalEmployee = employeeRepository.findByMatricule(matricule);
        Employee employee = optionalEmployee.orElseThrow(() ->
                new ExpressionException("Employee not found for matricule: \"" + matricule + "\"")
        );
        employee.addFormation(formation);
        formation.setEmployee(employee);
        formationRepository.save(formation);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        System.out.println(employees);
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ExpressionException("Employee not found for id: \"" + employeeId + "\""));
        employee.setNom(employeeDto.getNom());
        employee.setPrenom(employeeDto.getPrenom());
        employee.setEmail(employeeDto.getEmail());
        employee.setStatus(employeeDto.getStatus());
        employee.setSexe(employeeDto.getSexe());
        employee.setDateNaissance(employeeDto.getDateNaissance());
        employee.setTelephone(employeeDto.getTelephone());
        employee.setUserName(employeeDto.getUserName());
        employee.setMatricule(employeeDto.getMatricule());
        return employeeRepository.save(employee);
    }

    public Employee addCertification(Long id, Certification certification) {
        try {
            Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                    new ExpressionException("Employee not found for id: \"" + id + "\""));
            employee.getCertifications().add(certification);
            certification.setEmployee(employee);
            certificationRepository.save(certification);
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error while saving certification", e);
            return null;
        }
    }


    public Employee addExperience(Long id, Experience experience) {
        try {
            Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                    new ExpressionException("Employee not found for id: \"" + id + "\""));
            employee.getExperiences().add(experience);
            experience.setEmployee(employee);
            experienceRepository.save(experience);
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error while saving experience", e);
            return null;
        }
    }


    public Employee addEvaluation(Long id, Evaluation evaluation) {
        try {
            Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                    new ExpressionException("Employee not found for id: \"" + id + "\""));
            employee.getEvaluations().add(evaluation);
            evaluation.setEmployee(employee);
            evaluationRepository.save(evaluation);
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error while saving evaluation", e);
            return null;
        }
    }


    public Employee addPlanDeDeveloppement(Long id, PlanDeDeveloppement planDeDeveloppement) {
        try {
            Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                    new ExpressionException("Employee not found for id: \"" + id + "\""));
            employee.getPlanDeDeveloppements().add(planDeDeveloppement);
            planDeDeveloppement.setEmployee(employee);
            planDeDeveloppementRepository.save(planDeDeveloppement);
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error while saving plan de developpement", e);
            return null;
        }
    }


    public Employee addCompetence(Long id, Competences competences) {
        try {
            Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                    new ExpressionException("Employee not found for id: \"" + id + "\""));
            employee.getCompetences().add(competences);
            competences.getEmployees().add(employee);
            competencesRepository.save(competences);
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error while saving competence", e);
            return null;
        }
    }

    @Override
    public void remove(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee findByMatricule(String matricule) {
        return employeeRepository.findByMatricule(matricule).orElse(null);
    }
}
