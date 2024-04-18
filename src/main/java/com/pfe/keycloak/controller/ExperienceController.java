package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.ExperienceDto;
import com.pfe.keycloak.model.Experience;
import com.pfe.keycloak.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@Controller
@RequestMapping("/{matricule}/experience")
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/add")
    public ResponseEntity<Experience> addExperience(@PathVariable String matricule, @RequestBody ExperienceDto experience) {
        return ResponseEntity.ok(experienceService.addExperience(matricule, experience));

    }
    @DeleteMapping("/delete/{code}")
    public Response deleteExperience(@PathVariable String matricule, @PathVariable String code) {
        experienceService.removeExperience(matricule, code);
        return Response.ok().build();
    }
    @PutMapping("/update")
    public ResponseEntity<Experience> updateExperience(@PathVariable String matricule, @RequestBody ExperienceDto experience) {

        return ResponseEntity.ok(experienceService.updateExperience(matricule, experience));
    }
    @GetMapping("/find/{code}")
    public ResponseEntity<Experience> findExperienceByCode(@PathVariable String matricule, @PathVariable String code) {

        return ResponseEntity.ok(experienceService.findByCode(code));
    }
    @GetMapping("/all")
    public ResponseEntity<Iterable<Experience>> findAllExperience(@PathVariable String matricule) {

        return ResponseEntity.ok( experienceService.findAll(matricule));

    }
}
