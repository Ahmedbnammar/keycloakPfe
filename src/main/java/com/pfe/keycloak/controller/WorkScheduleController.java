package com.pfe.keycloak.controller;


import com.pfe.keycloak.dto.WorkScheduleDto;
import com.pfe.keycloak.model.WorkSchedule;
import com.pfe.keycloak.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/work-schedules")
public class WorkScheduleController {

    @Autowired
    private WorkScheduleService workScheduleService;

    // Endpoint to add or update a work schedule
    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WorkSchedule> saveWorkSchedule(@RequestBody WorkScheduleDto workSchedule) {


        WorkSchedule savedSchedule = workScheduleService.saveWorkSchedule(workSchedule);
        return ResponseEntity.ok(savedSchedule);
    }

    // Endpoint to get all work schedules for an employee
    @GetMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<WorkSchedule>> getWorkSchedulesByEmployeeId(@RequestBody WorkScheduleDto workSchedule) {
        List<WorkSchedule> schedules = workScheduleService.getWorkSchedulesByEmployeeId(workSchedule.getEmployee());
        return ResponseEntity.ok(schedules);
    }

    // Endpoint to get schedules by employee and date
    @GetMapping("/employee/date")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<WorkSchedule>> getWorkSchedulesByDate(@RequestBody WorkScheduleDto workSchedule) {
        List<WorkSchedule> schedules = workScheduleService.getWorkSchedulesByEmployeeIdAndDate(workSchedule.getEmployee(), workSchedule.getWorkDate());
        return ResponseEntity.ok(schedules);
    }


    @DeleteMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteWorkSchedule(@RequestBody WorkScheduleDto workSchedule) {
        workScheduleService.deleteWorkSchedule(workSchedule.getId());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/check-date")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> checkDateAvailability(@RequestBody WorkScheduleDto workSchedule) {
        boolean isAvailable = !workScheduleService.isDateInUse(workSchedule.getEmployee(), workSchedule.getWorkDate());
        if (isAvailable) {
            return ResponseEntity.ok("Date is available for scheduling.");
        } else {
            return ResponseEntity.badRequest().body("Date is already in use.");
        }
    }

    // Optional: Endpoint to find a work schedule by ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WorkSchedule> findWorkScheduleById(@RequestBody WorkScheduleDto workScheduleDto) {
        Optional<WorkSchedule> workSchedule = workScheduleService.findWorkScheduleById(workScheduleDto.getId());
        return workSchedule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}