package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.PlanDeDeveloppement;
import com.pfe.keycloak.service.PlanDeDeveloppementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{matricule}/planDeDeveloppement")
public class PlanDeDeveloppementController {
    @Autowired
    private PlanDeDeveloppementService planDeDeveloppementService;

    @PostMapping("/add")
    public PlanDeDeveloppement addPlanDeDeveloppement(@PathVariable String matricule,@RequestBody PlanDeDeveloppementDto planDeDeveloppement)
    {
        return planDeDeveloppementService.addPlanDeDeveloppement(matricule,planDeDeveloppement);


    }
    @PutMapping("/update")
    public PlanDeDeveloppement updatePlanDeDeveloppement(@PathVariable String matricule,@RequestBody PlanDeDeveloppementDto planDeDeveloppement)
    {
        return planDeDeveloppementService.updatePlanDeDeveloppement(matricule,planDeDeveloppement);


    }
    @DeleteMapping("/delete/{id}")
    public void deletePlanDeDeveloppement(@PathVariable Long id)
    {
        planDeDeveloppementService.removePlanDeDeveloppement(id);


    }
    @GetMapping("/find/{id}")
    public PlanDeDeveloppement findPlanDeDeveloppement(@PathVariable Long id)
    {
        return planDeDeveloppementService.findPlanDeDeveloppement(id);


    }
    @GetMapping("/findall")
    public Iterable<PlanDeDeveloppement> findAllPlanDeDeveloppement(@PathVariable String matricule)
    {
        return planDeDeveloppementService.findAllPlanDeDeveloppement(matricule);


    }


}
