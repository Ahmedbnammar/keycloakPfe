package com.pfe.keycloak.controller;

import com.pfe.keycloak.dto.FormationDto;
import com.pfe.keycloak.model.Formation;
import com.pfe.keycloak.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;


@RestController
@RequestMapping("/formation")
public class FormationController {
    @Autowired
    private FormationService formationService;

    @PostMapping("/add/{matricule}")
    public ResponseEntity<Formation> addFormation(@RequestBody FormationDto formation, @PathVariable String matricule){

        return ResponseEntity.ok(formationService.addFormation(matricule,formation));
    }

    @PutMapping("/update")
    public ResponseEntity<Formation> updateFormation(@RequestBody FormationDto formation){
        return ResponseEntity.ok(formationService.updateFormation(formation));
    }

    @DeleteMapping("/delete/{matricule}")
    public Response deleteFormation(@PathVariable Long id, @PathVariable String matricule){
        formationService.removeFormation(matricule,id);
        return Response.ok().build();
    }

    @GetMapping("/find/")
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
