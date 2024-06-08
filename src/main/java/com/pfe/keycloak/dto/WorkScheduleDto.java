package com.pfe.keycloak.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.pfe.keycloak.model.Enum;
import com.pfe.keycloak.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkScheduleDto {

    private Long id;

    private Long employee;

    private Date workDate;

    private List<Task> tasks;

    private Enum.TaskStatus status;
    private Enum.TaskComplexity complexity;
    private int priority;
    private Duration estimatedDuration;
    private Duration actualDuration;


}
