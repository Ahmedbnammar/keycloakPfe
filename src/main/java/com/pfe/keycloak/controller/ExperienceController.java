package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.ExperienceDto;
import com.pfe.keycloak.model.Experience;
import com.pfe.keycloak.service.ExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@Controller
@RequestMapping("/{id}/experience")
public class ExperienceController {
    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<Experience> addExperience(@PathVariable long id, @RequestBody ExperienceDto experience) {
        return ResponseEntity.ok(experienceService.addExperience(id, experience));

    }

    @DeleteMapping("/{code}")
    public Response deleteExperience(@PathVariable long code, @PathVariable String id) {
        experienceService.removeExperience( code);
        return Response.ok().build();
    }

    @PutMapping
    public ResponseEntity<Experience> updateExperience(@PathVariable long id, @RequestBody ExperienceDto experience) {

        return ResponseEntity.ok(experienceService.updateExperience(id, experience));
    }

    @GetMapping("{code}")
    public ResponseEntity<Experience> findExperienceByCode(@PathVariable long id, @PathVariable String code) {

        return ResponseEntity.ok(experienceService.findByCode(id, code));
    }

    @GetMapping
    public ResponseEntity<Iterable<Experience>> findAllExperience(@PathVariable long id) {

        return ResponseEntity.ok(experienceService.findAll(id));

    }
}
