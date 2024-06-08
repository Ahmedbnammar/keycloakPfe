package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.PlanDeDeveloppement;
import com.pfe.keycloak.service.PlanDeDeveloppementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/planDeDeveloppement")
public class PlanDeDeveloppementController {
    @Autowired
    private PlanDeDeveloppementService planDeDeveloppementService;

    @PostMapping
    ResponseEntity<PlanDeDeveloppement> addPlanDeDeveloppement(@PathVariable Long id, @RequestBody PlanDeDeveloppementDto planDeDeveloppement) {
        return ResponseEntity.ok(planDeDeveloppementService.addPlanDeDeveloppement(id, planDeDeveloppement));


    }

    @PutMapping("/update")
    ResponseEntity<PlanDeDeveloppement> updatePlanDeDeveloppement(@PathVariable Long id, @RequestBody PlanDeDeveloppementDto planDeDeveloppement) {
        return ResponseEntity.ok(planDeDeveloppementService.updatePlanDeDeveloppement(id, planDeDeveloppement));


    }

    @DeleteMapping("/delete/{matricule}")
    ResponseEntity<Void> deletePlanDeDeveloppement(@PathVariable Long matricule) {
        planDeDeveloppementService.removePlanDeDeveloppement(matricule);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/find/{matricule}")
    ResponseEntity<PlanDeDeveloppement> findPlanDeDeveloppement(@PathVariable Long matricule) {
        return ResponseEntity.ok( planDeDeveloppementService.findPlanDeDeveloppement(matricule));


    }

    @GetMapping("/findall")
    public  ResponseEntity<Iterable<PlanDeDeveloppement>> findAllPlanDeDeveloppement(@PathVariable Long id) {
        return ResponseEntity.ok(planDeDeveloppementService.findAllPlanDeDeveloppement(id));


    }


}
