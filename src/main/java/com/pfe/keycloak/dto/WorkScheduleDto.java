package com.pfe.keycloak.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.pfe.keycloak.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkScheduleDto {

    private Long id;

    private Employee employee;

    private Date workDate;

    private Map<String, String> tasks;

    private String status;


}
