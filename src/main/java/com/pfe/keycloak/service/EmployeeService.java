package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.EmployeeDto;
import com.pfe.keycloak.dto.SignUpDto;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.model.Formation;

import java.util.List;


public interface EmployeeService {

    Employee register(Employee employee);
    void remove(Long employeeId);
    Employee update(Employee employee);
    Employee findById(Long id);
    Employee findByMatricule(String matricule);
    Employee addFormation(String matricule, Formation formation);

    List<Employee> getAll();

    void deleteEmployee(Long id);

    Employee updateEmployee(Long employeeId, EmployeeDto employee);
}