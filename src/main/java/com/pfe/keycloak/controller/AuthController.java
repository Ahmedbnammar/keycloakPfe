package com.pfe.keycloak.controller;


import com.pfe.keycloak.dto.ResetPasswordDto;
import com.pfe.keycloak.dto.ResetPasswordRequestDto;
import com.pfe.keycloak.service.AuthService;
import com.pfe.keycloak.dto.LoginRequestDto;
import com.pfe.keycloak.dto.SignUpDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest.getUsername(), loginRequest.getPassword())
                .map(token -> ResponseEntity.ok().body(token))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body("{\"error\": \"invalid_grant\", \"error_description\": \"Invalid user credentials\"}")));
    }

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping(path = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<UserRepresentation>> getAllUsers() {
        List<UserRepresentation> allUsers = authService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PreAuthorize("hasRole('HR')")
    @PostMapping(path = "/reset-password", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordRequest) {
        return authService.resetPassword(resetPasswordRequest.getUsername(), resetPasswordRequest.getNewPassword());
    }
    @PostMapping(path="/reset-password-request")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody ResetPasswordRequestDto username) {
        return authService.resetPasswordRequest(username.getUsername()) ;
    }
    @PostMapping(path="/setPinForUser")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> setPinForUser(@RequestBody ResetPasswordDto resetPasswordRequest) {
        return authService.setPinForUser(resetPasswordRequest.getUsername(), resetPasswordRequest.getNewPassword());
    }
    @PostMapping(path="/checkPin")
    @PreAuthorize("isAuthenticated()")
    public Boolean checkPinForUser(@RequestBody ResetPasswordDto resetPasswordRequest) {
        return authService.checkPinForUser(resetPasswordRequest.getUsername(), resetPasswordRequest.getNewPassword());
    }
}
