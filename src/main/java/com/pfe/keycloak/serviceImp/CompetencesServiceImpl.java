package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.dto.CompetenceDto;
import com.pfe.keycloak.model.Competences;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.model.Task;
import com.pfe.keycloak.repository.CompetencesRepository;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.repository.TaskRepository;
import com.pfe.keycloak.service.CompetencesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class CompetencesServiceImpl implements CompetencesService {


    private final CompetencesRepository competencesRepository;

    private final EmployeeRepository employeeRepository;

    private final TaskRepository taskRepository;

    public CompetencesServiceImpl (CompetencesRepository competencesRepository, EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.competencesRepository = competencesRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }
    @Override
    public Competences createCompetence(CompetenceDto competenceDto) {
        Competences competence = new Competences(competenceDto);
        return competencesRepository.save(competence);
    }

    @Override
    public Competences getCompetenceById(Long id) {
        // Logic to fetch a competence by id
        return competencesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competence not found"));
    }

    @Override
    public List<Competences> getAllCompetences() {
        System.out.println( competencesRepository.findAll());
        return competencesRepository.findAll();
    }

    @Override
    public Competences updateCompetence(Competences competence) {

        Competences existingCompetence = getCompetenceById(competence.getId());

        return competencesRepository.save(existingCompetence);
    }

    @Transactional
    public void deleteCompetence(Long id) {
        Competences competence = competencesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competence not found with id " + id));


        for (Employee employee : competence.getEmployees()) {
            assert employee.getCompetences() != null;
            employee.getCompetences().remove(competence);
            employeeRepository.save(employee);
        }

        for (Task task : competence.getTasks()) {
            assert task.getCompetences() != null;
            task.getCompetences().remove(competence);
            taskRepository.save(task);
        }

        competencesRepository.deleteById(id);
    }

    @Override
    public Competences addEmployeeToCompetence(Long competenceId, Long employeeId) {
        Competences competence = getCompetenceById(competenceId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        competence.getEmployees().add(employee);
        employee.getCompetences().add(competence);
        employeeRepository.save(employee);
        return competencesRepository.save(competence);

    }

    @Override
    public Competences removeEmployeeFromCompetence(Long competenceId, Long employeeId) {
        Competences competence = getCompetenceById(competenceId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        competence.getEmployees().remove(employee);
        return competencesRepository.save(competence);
    }
}
