package com.pfe.keycloak.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date workDate;

    @ElementCollection
    @CollectionTable(name = "task_entries", joinColumns = {@JoinColumn(name = "schedule_id")})
    @MapKeyColumn(name = "hour")
    @Column(name = "task")
    private Map<String, String> tasks;

    @Column(nullable = false)
    private String status;


}
