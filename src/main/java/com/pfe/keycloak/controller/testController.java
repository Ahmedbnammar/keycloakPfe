package com.pfe.keycloak.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keycloak")
public class testController {
    @GetMapping("/hello")
    @PreAuthorize("hasRole('HR')")
    public String hello() {
        return "hello this is a hello method for testing the controller";
    }
}
