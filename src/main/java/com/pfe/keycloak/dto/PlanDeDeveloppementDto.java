package com.pfe.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanDeDeveloppementDto {
    @NotNull
    private Long id;
    private String code;
    private String objectif;
    private String actions;
    private Date dateDebut;
    private Date dateFin;
}
