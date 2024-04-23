package com.pfe.keycloak.repository;

import com.pfe.keycloak.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
    List<WorkSchedule> findByEmployeeId(Long employeeId);
    List<WorkSchedule> findByEmployeeIdAndWorkDate(Long employeeId, Date workDate);
}
