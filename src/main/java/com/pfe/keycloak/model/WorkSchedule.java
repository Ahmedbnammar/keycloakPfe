package com.pfe.keycloak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pfe.keycloak.dto.WorkScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_schedules")
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date workDate;

    @ElementCollection
    @CollectionTable(name = "task_entries", joinColumns = {@JoinColumn(name = "schedule_id")})
    @MapKeyColumn(name = "hour")
    @Column(name = "task")
    private Map<String, String> tasks;

    @Column(nullable = false)
    private String status;

    public WorkSchedule(WorkScheduleDto workScheduleDto, Employee employee) {
        this.id = workScheduleDto.getId();
        this.workDate = workScheduleDto.getWorkDate();
        this.tasks = workScheduleDto.getTasks();
        this.status = workScheduleDto.getStatus();
        this.employee = employee;
    }


}
