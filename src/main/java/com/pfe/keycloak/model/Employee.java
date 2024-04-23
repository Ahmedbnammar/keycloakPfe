package com.pfe.keycloak.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pfe.keycloak.dto.SignUpDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @ManyToMany(targetEntity = Competences.class)
    @Nullable
    private List<Competences> competences;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Nullable
    @JsonManagedReference
    private List<Experience> experiences;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    @JsonManagedReference
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Nullable
    @JsonManagedReference
    private List<PlanDeDeveloppement> planDeDeveloppements;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Nullable
    @JsonManagedReference
    private List<Formation> formations;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Nullable
    @JsonManagedReference
    private List<Certification> certifications;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<WorkSchedule> workSchedules;

    public Employee(SignUpDto signUpDto) {
        this.nom = signUpDto.getLastname();
        this.prenom = signUpDto.getFirstname();
        this.email = signUpDto.getEmail();
        this.sexe = signUpDto.getGender();
        this.dateNaissance = signUpDto.getBirthDate();
        this.telephone = signUpDto.getPhoneNumber();
    }

    public void addFormation(Formation formation) {
        assert this.formations != null;
        this.formations.add(formation);
        formation.setEmployee(this);
    }

    public void removeFormation(Formation formation) {
        try {
            assert this.formations != null;
            this.formations.remove(formation);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Certification findCertificationById(Long id) {
        try {
            assert this.certifications != null;
            for (Certification certification : this.certifications) {
                if (Objects.equals(certification.getId(), id)) {
                    return certification;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void removeCertificationById(Long id) {
        try {
            assert this.certifications != null;
            for (Certification certification : this.certifications) {
                if (Objects.equals(certification.getId(), id)) {
                    this.certifications.remove(certification);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCertification(Certification certification) {
        assert this.certifications != null;
        this.certifications.add(certification);
        certification.setEmployee(this);
    }

    public Evaluation findEvaluationById(Long id) {
        try {
            assert this.evaluations != null;
            for (Evaluation evaluation : this.evaluations) {
                if (Objects.equals(evaluation.getId(), id)) {
                    return evaluation;
                }

            }

        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
        return null;
    }

    public void addEvaluation(Evaluation evaluation) {
        assert this.evaluations != null;
        this.evaluations.add(evaluation);
        evaluation.setEmployee(this);
    }

    public Evaluation findEvaluationByCode(String code) {
        try {
            assert this.evaluations != null;
            for (Evaluation evaluation : this.evaluations) {
                if (Objects.equals(evaluation.getCode(), code)) {
                    return evaluation;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Certification findCertificationByCode(String code) {
        try {
            assert this.certifications != null;
            for (Certification certification : this.certifications) {
                if (Objects.equals(certification.getCode(), code)) {
                    return certification;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addExperience(Experience experience1) {
        assert this.experiences != null;
        this.experiences.add(experience1);
        experience1.setEmployee(this);
    }

    public Experience findExperienceByCode(String code) {
        try {
            assert this.experiences != null;
            for (Experience experience : this.experiences) {
                if (Objects.equals(experience.getCode(), code)) {
                    return experience;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public void removeExperience(String code) {
        try {
            assert this.experiences != null;
            for (Experience experience : this.experiences) {
                if (Objects.equals(experience.getCode(), code)) {
                    this.experiences.remove(experience);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPlanDeDeveloppement(PlanDeDeveloppement planDeDeveloppement1) {
        assert this.planDeDeveloppements != null;
        this.planDeDeveloppements.add(planDeDeveloppement1);
        planDeDeveloppement1.setEmployee(this);

    }

    public PlanDeDeveloppement findPlanDeDeveloppementByCode(String code) {
        try {
            assert this.planDeDeveloppements != null;
            for (PlanDeDeveloppement planDeDeveloppement : this.planDeDeveloppements) {
                if (Objects.equals(planDeDeveloppement.getCode(), code)) {
                    return planDeDeveloppement;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addWorkSchedule(WorkSchedule workSchedule) {
        if (this.workSchedules == null) {
            this.workSchedules = new ArrayList<>();
        }
        this.workSchedules.add(workSchedule);
        workSchedule.setEmployee(this);
    }

    public void removeWorkSchedule(WorkSchedule workSchedule) {
        if (this.workSchedules != null) {
            this.workSchedules.remove(workSchedule);
        }
    }

}
