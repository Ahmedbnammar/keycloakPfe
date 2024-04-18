package com.pfe.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Setter
public class EmployeeDto {
    private Long id;

    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String status;
    private String sexe;
    private Date dateNaissance;
    private String telephone;
    private String userName;
}
