package com.pfe.keycloak.repository;

import com.pfe.keycloak.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
    List<WorkSchedule> findByEmployeeId(Long employeeId);
    List<WorkSchedule> findByEmployeeIdAndWorkDate(Long employeeId, Date workDate);
}
