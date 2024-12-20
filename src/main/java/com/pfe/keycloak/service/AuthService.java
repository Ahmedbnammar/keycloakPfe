package com.pfe.keycloak.service;

import com.pfe.keycloak.dto.SignUpDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AuthService {
    ResponseEntity<?> signUp(SignUpDto signUpDto);

    public Mono<String> login(String username, String password);
    public Mono<String> obtainAdminToken();
    public List<UserRepresentation> getAllUsers();
    public ResponseEntity<?> resetPassword(String username, String newPassword);
    public ResponseEntity<?> resetPasswordRequest(String username);
    public Boolean setPinForUser(String username, String pin);
    public ResponseEntity<?> checkPinForUser(String username, String pin);
    public Boolean isPinExist(String username);
    public Boolean isAccountLocked(String username);
}
