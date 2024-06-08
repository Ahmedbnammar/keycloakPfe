package com.pfe.keycloak.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pfe.keycloak.model.*;

import java.io.IOException;

public class EmployeeSerializer extends StdSerializer<Employee> {

    public EmployeeSerializer() {
        this(null);
    }

    public EmployeeSerializer(Class<Employee> t) {
        super(t);
    }

    @Override
    public void serialize(Employee employee, JsonGenerator gen, SerializerProvider provider) throws IOException {
        try {
            gen.writeStartObject();

            // Basic fields
            gen.writeNumberField("id", employee.getId());
            gen.writeStringField("matricule", employee.getMatricule());
            gen.writeStringField("nom", employee.getNom());
            gen.writeStringField("prenom", employee.getPrenom());
            gen.writeStringField("email", employee.getEmail());
            gen.writeStringField("role", employee.getRole());
            gen.writeStringField("status", employee.getStatus());
            gen.writeStringField("sexe", employee.getSexe());
            gen.writeObjectField("dateNaissance", employee.getDateNaissance());
            gen.writeStringField("telephone", employee.getTelephone());
            gen.writeStringField("userName", employee.getUserName());

            // Competences
            gen.writeArrayFieldStart("competences");
            for (Competences competence : employee.getCompetences()) {
                gen.writeStartObject();
                gen.writeNumberField("id", competence.getId());
                gen.writeStringField("code", competence.getCode());
                gen.writeStringField("nom", competence.getNom());
                gen.writeStringField("description", competence.getDescription());
                gen.writeNumberField("level", competence.getLevel());
                gen.writeEndObject();
            }
            gen.writeEndArray();

            // Experiences
            gen.writeArrayFieldStart("experiences");
            for (Experience experience : employee.getExperiences()) {
                gen.writeStartObject();
                gen.writeNumberField("id", experience.getId());
                gen.writeStringField("code", experience.getCode());
                gen.writeObjectField("dateDebut", experience.getDateDebut());
                gen.writeObjectField("dateFin", experience.getDateFin());
                gen.writeStringField("description", experience.getDescription());
                gen.writeEndObject();
            }
            gen.writeEndArray();

            // Evaluations
            gen.writeArrayFieldStart("evaluations");
            for (Evaluation evaluation : employee.getEvaluations()) {
                gen.writeStartObject();
                gen.writeNumberField("id", evaluation.getId());
                gen.writeStringField("code", evaluation.getCode());
                gen.writeStringField("description", evaluation.getDescription());
                gen.writeNumberField("resultat", evaluation.getResultat());
                gen.writeObjectField("dateEvaluation", evaluation.getDateEvaluation());
                gen.writeEndObject();
            }
            gen.writeEndArray();

            // PlanDeDeveloppements
            gen.writeArrayFieldStart("planDeDeveloppements");
            for (PlanDeDeveloppement plan : employee.getPlanDeDeveloppements()) {
                gen.writeStartObject();
                gen.writeNumberField("id", plan.getId());
                gen.writeStringField("code", plan.getCode());
                gen.writeStringField("objectif", plan.getObjectif());
                gen.writeStringField("actions", plan.getActions());
                gen.writeObjectField("dateDebut", plan.getDateDebut());
                gen.writeObjectField("dateFin", plan.getDateFin());
                gen.writeEndObject();
            }
            gen.writeEndArray();

            // Formations
            gen.writeArrayFieldStart("formations");
            for (Formation formation : employee.getFormations()) {
                gen.writeStartObject();
                gen.writeNumberField("id", formation.getId());
                gen.writeStringField("code", formation.getCode());
                gen.writeStringField("titre", formation.getTitre());
                gen.writeStringField("description", formation.getDescription());
                gen.writeObjectField("dateDebut", formation.getDateDebut());
                gen.writeObjectField("dateFin", formation.getDateFin());
                gen.writeEndObject();
            }
            gen.writeEndArray();

            // Certifications
            gen.writeArrayFieldStart("certifications");
            for (Certification certification : employee.getCertifications()) {
                gen.writeStartObject();
                gen.writeNumberField("id", certification.getId());
                gen.writeStringField("code", certification.getCode());
                gen.writeStringField("titre", certification.getTitre());
                gen.writeObjectField("dateAcquisition", certification.getDateAcquisition());
                gen.writeStringField("description", certification.getDescription());
                gen.writeEndObject();
            }
            gen.writeEndArray();

            // WorkSchedules
            gen.writeArrayFieldStart("workSchedules");
            for (WorkSchedule workSchedule : employee.getWorkSchedules()) {
                gen.writeStartObject();
                gen.writeNumberField("id", workSchedule.getId());
                gen.writeObjectField("workDate", workSchedule.getWorkDate());
                gen.writeStringField("status", workSchedule.getStatus().name());
                gen.writeNumberField("priority", workSchedule.getPriority());
                gen.writeArrayFieldStart("tasks");
                for (Task task : workSchedule.getTasks()) {
                    gen.writeStartObject();
                    gen.writeNumberField("id", task.getId());
                    gen.writeStringField("name", task.getName());
                    gen.writeStringField("description", task.getDescription());
                    gen.writeStringField("status", task.getStatus().name());
                    gen.writeNumberField("priority", task.getPriority());
                    gen.writeStringField("complexity", task.getComplexity().name());
                    gen.writeObjectField("estimatedDuration", task.getEstimatedDuration());
                    gen.writeObjectField("actualDuration", task.getActualDuration());
                    gen.writeEndObject();
                }
                gen.writeEndArray();
                gen.writeEndObject();
            }
            gen.writeEndArray();

            gen.writeEndObject();
        } catch (Exception e) {
            throw new IOException("Error serializing Employee object", e);
        }
    }
}
