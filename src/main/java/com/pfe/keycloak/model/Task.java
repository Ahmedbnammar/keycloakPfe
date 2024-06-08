package com.pfe.keycloak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(name = "task_competences", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "competence_id"))
    private List<Competences> requiredCompetences;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    @JsonBackReference
    private WorkSchedule workSchedule;

    @Enumerated(EnumType.STRING)
    private Enum.TaskStatus status;

    @Column(nullable = false)
    private Integer priority;

    @Column(nullable = false)
    private Duration estimatedDuration;

    @Column
    private Duration actualDuration;

    @ManyToMany(targetEntity = Competences.class)
    @Nullable
    private List<Competences> competences = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Enum.TaskComplexity complexity;
}
