package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.model.WorkSchedule;
import com.pfe.keycloak.repository.WorkScheduleRepository;
import com.pfe.keycloak.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkScheduleServiceImpl implements WorkScheduleService {

    @Autowired
    private WorkScheduleRepository workScheduleRepository;


    @Transactional
    public WorkSchedule saveWorkSchedule(WorkSchedule workSchedule) {
        return workScheduleRepository.save(workSchedule);
    }


    public List<WorkSchedule> getWorkSchedulesByEmployeeId(Long employeeId) {
        return workScheduleRepository.findByEmployeeId(employeeId);
    }


    public List<WorkSchedule> getWorkSchedulesByEmployeeIdAndDate(Long employeeId, Date workDate) {
        return workScheduleRepository.findByEmployeeIdAndWorkDate(employeeId, workDate);
    }


    @Transactional
    public void deleteWorkSchedule(Long scheduleId) {
        workScheduleRepository.deleteById(scheduleId);
    }


    public Optional<WorkSchedule> findWorkScheduleById(Long id) {
        return workScheduleRepository.findById(id);
    }
    public boolean isDateInUse(Long employeeId, Date workDate) {
        List<WorkSchedule> schedules = workScheduleRepository.findByEmployeeIdAndWorkDate(employeeId, workDate);
        return !schedules.isEmpty();
    }
}