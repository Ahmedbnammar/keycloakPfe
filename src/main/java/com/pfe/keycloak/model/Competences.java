package com.pfe.keycloak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pfe.keycloak.dto.CompetenceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "competences")
public class Competences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String nom;
    private String description;
    private int level;

    @ManyToMany(mappedBy = "requiredCompetences")
    @JsonBackReference
    private List<Task> tasks;

    @ManyToMany(mappedBy = "competences")
    @JsonBackReference
    private List<Employee> employees;


    public Competences(CompetenceDto competenceDto) {
        this.code = competenceDto.getCode();
        this.nom = competenceDto.getNom();
        this.description = competenceDto.getDescription();
        this.level = competenceDto.getLevel();
    }
}
