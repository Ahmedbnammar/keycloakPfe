package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.dto.ExperienceDto;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.model.Experience;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.repository.ExperienceRepository;
import com.pfe.keycloak.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    public ExperienceRepository experienceRepository;

    @Autowired
    public EmployeeRepository employeeRepository;


    @Override
    public Experience addExperience(String matricule, ExperienceDto experience) {
        Employee employee = employeeRepository.findByMatricule(matricule).orElseThrow(() ->
                new ExpressionException("Employee not found for matricule: \"" + matricule + "\"")
        );
        Experience experience1 = new Experience();
        experience1.setCode(experience.getCode());
        experience1.setDateDebut(experience.getDateDebut());
        experience1.setDateFin(experience.getDateFin());
        experience1.setDescription(experience.getDescription());
        employee.addExperience(experience1);
        experience1.setEmployee(employee);
        return experienceRepository.save(experience1);
    }

    @Override
    public void removeExperience(String matricule, String code) {
        Employee employee = employeeRepository.findByMatricule(matricule).orElseThrow(() ->
                new ExpressionException("Employee not found for matricule: \"" + matricule + "\"")
        );
        employee.removeExperience(code);
    }

    @Override
    public Experience updateExperience(String matricule, ExperienceDto experience) {
        Employee employee = employeeRepository.findByMatricule(matricule).orElseThrow(() ->
                new ExpressionException("Employee not found for matricule: \"" + matricule + "\"")
        );
        Experience experience1 = employee.findExperienceByCode(experience.getCode());
        experience1.setCode(experience.getCode());
        experience1.setDateDebut(experience.getDateDebut());
        experience1.setDateFin(experience.getDateFin());
        experience1.setDescription(experience.getDescription());
        return experienceRepository.save(experience1);
    }

    @Override
    public Experience findByCode(String code) {
        return employeeRepository.findByMatricule(code).orElseThrow(() ->
                new ExpressionException("Employee not found for matricule: \"" + code + "\"")
        ).findExperienceByCode(code);
    }

    @Override
    public List<Experience> findAll(String matricule) {
        return employeeRepository.findByMatricule(matricule).orElseThrow(() ->
                new ExpressionException("Employee not found for matricule: \"" + matricule + "\"")
        ).getExperiences();
    }
}
