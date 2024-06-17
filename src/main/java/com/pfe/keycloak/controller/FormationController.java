package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.FormationDto;
import com.pfe.keycloak.dto.PlanDeDeveloppementDto;
import com.pfe.keycloak.model.Formation;
import com.pfe.keycloak.model.PlanDeDeveloppement;
import com.pfe.keycloak.service.FormationService;
import com.pfe.keycloak.serviceImp.FormationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;


@RestController
@RequestMapping("/formation")
public class FormationController {
    @Autowired
    private FormationService formationService;

    @Autowired
    private FormationServiceImpl formationServiceImpl;

   /* @PostMapping("/add/{matricule}")
    public ResponseEntity<Formation> addFormation(@RequestBody FormationDto formation, @PathVariable String matricule){

        return ResponseEntity.ok(formationService.addFormation(matricule,formation));
    }*/
   @PostMapping
   public ResponseEntity<Formation> addFormation(@PathVariable Long id, @RequestBody FormationDto formation) {
       Formation addedFormation = formationService.addFormation(id, formation);
       return ResponseEntity.ok(addedFormation);
   }
    @PutMapping
    public ResponseEntity<Formation> updateFormation(@RequestBody FormationDto formation){
        return ResponseEntity.ok(formationService.updateFormation(formation));
    }

    @DeleteMapping
    public Response deleteFormation( @RequestBody FormationDto formation){
        formationService.removeFormation(formation.getId());
        return Response.ok().build();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Formation> findFormation(@PathVariable Long id){
        Formation formation = formationService.findFormation(id);
        return ResponseEntity.ok(formation);
    }
    @GetMapping("/findall/{id}")
    public ResponseEntity<Iterable<Formation>> findAllFormation(@PathVariable Long id){
        Iterable<Formation> formation = formationService.findAllFormation(id);
        return ResponseEntity.ok(formation);
    }

}
