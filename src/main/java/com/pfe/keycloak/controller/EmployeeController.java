package com.pfe.keycloak.controller;


import com.pfe.keycloak.dto.EmployeeDto;
import com.pfe.keycloak.dto.EvaluationDto;
import com.pfe.keycloak.dto.SignUpDto;
import com.pfe.keycloak.model.*;
import com.pfe.keycloak.service.EmployeeService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/{matricule}/addFormation")
    public ResponseEntity<Employee> addFormationToEmployee(
            @PathVariable String matricule,
            @RequestBody Formation formation) {
        Employee employee = employeeService.addFormation(matricule, formation);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }
    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody Employee employee) {
        try {
            Employee registeredEmployee = employeeService.register(employee);
            return new ResponseEntity<>(registeredEmployee, HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
       public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeDto employee,
                                                       @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }
}