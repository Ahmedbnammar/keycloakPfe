package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.EmployeeDto;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.model.Formation;
import com.pfe.keycloak.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static final String FLASK_ENDPOINT = "http://localhost:5000/employees";
    private final EmployeeService employeeService;
    @Autowired
    private RestTemplate restTemplate;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

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

  /*  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEmployee() {
        logger.info("Fetching all employees from Flask endpoint");

        try {
            ResponseEntity<List> response = restTemplate.getForEntity(FLASK_ENDPOINT, List.class);
            List<Map<String, Object>> employees = response.getBody();

            if (employees == null) {
                logger.warn("No employees found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found");
            }

            logger.info("Number of employees fetched: {}", employees.size());

            // Log individual employee details
            for (Map<String, Object> employee : employees) {
                logger.debug("Employee: {}", employee);
            }

            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error fetching employees from Flask endpoint", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching employees");
        }
    }
*/
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                                   @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeDto));
    }

    @GetMapping("/check-free-employees")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> checkFreeEmployees(@RequestParam String date) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:5000/api/free-employees?date=" + date;
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        return ResponseEntity.ok(response.getBody());
    }
}
