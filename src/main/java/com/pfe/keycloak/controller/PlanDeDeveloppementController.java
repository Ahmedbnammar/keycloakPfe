package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.PlanDeDeveloppement;
import com.pfe.keycloak.service.PlanDeDeveloppementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/planDeDeveloppement")
public class PlanDeDeveloppementController {
    @Autowired
    private PlanDeDeveloppementService planDeDeveloppementService;

    @PostMapping("/add")
    public PlanDeDeveloppement addPlanDeDeveloppement(@PathVariable Long id,@RequestBody PlanDeDeveloppementDto planDeDeveloppement)
    {
        return planDeDeveloppementService.addPlanDeDeveloppement(id,planDeDeveloppement);


    }
    @PutMapping("/update")
    public PlanDeDeveloppement updatePlanDeDeveloppement(@PathVariable Long id,@RequestBody PlanDeDeveloppementDto planDeDeveloppement)
    {
        return planDeDeveloppementService.updatePlanDeDeveloppement(id,planDeDeveloppement);


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
    public Iterable<PlanDeDeveloppement> findAllPlanDeDeveloppement(@PathVariable Long id)
    {
        return planDeDeveloppementService.findAllPlanDeDeveloppement(id);


    }


}
