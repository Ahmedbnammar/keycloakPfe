package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.dto.FormationDto;
import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.model.Formation;
import com.pfe.keycloak.model.PlanDeDeveloppement;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.repository.FormationRepository;
import com.pfe.keycloak.service.FormationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class FormationServiceImpl implements FormationService {

    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public FormationRepository formationRepository;

    @Override
    public Formation addFormation(Long matricule, FormationDto formation) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(matricule);

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
        employeeRepository.save(employee);
        return (formationRepository.save(formation1));


    }



    @Override
    public void removeFormation(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.ifPresent(employee -> {
            if(formationRepository.findById(id).isPresent()){
                Formation for1 = formationRepository.findById(id).get();
                employee.removeFormation(for1);
                employeeRepository.save(employee);
                formationRepository.deleteById(id);
            }
        });

    }

    @Override
    public Formation updateFormation( FormationDto formation) {
        Formation formation1 = formationRepository.findById(formation.getId()).orElseThrow(() ->
                new ExpressionException("Formation not found for Code formation : \"" + formation.getCode() + "\"")
        );
        formation1.setTitre(formation.getTitre());
        formation1.setDescription(formation.getDescription());
        formation1.setDateDebut(formation.getDateDebut());
        return(formationRepository.save(formation1));
    }

    @Override
    public Formation findFormation( Long id) {
        return formationRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Formation> findAllFormation(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new ExpressionException("Employee not found for id : \"" + id + "\"")
        );
        return employee.getFormations();
    }
}
