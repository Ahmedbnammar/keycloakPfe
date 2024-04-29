package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.WorkScheduleDto;
import com.pfe.keycloak.model.WorkSchedule;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WorkScheduleService {
    WorkSchedule saveWorkSchedule(WorkScheduleDto workSchedule);

    List<WorkSchedule> getWorkSchedulesByEmployeeId(Long employeeId);

    List<WorkSchedule> getWorkSchedulesByEmployeeIdAndDate(Long employeeId, Date workDate);

    void deleteWorkSchedule(Long scheduleId);

    Optional<WorkSchedule> findWorkScheduleById(Long id);

    boolean isDateInUse(Long employeeId, Date workDate);
}
