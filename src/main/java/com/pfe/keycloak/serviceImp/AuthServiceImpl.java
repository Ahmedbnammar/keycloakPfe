package com.pfe.keycloak.serviceImp;

import com.pfe.keycloak.TokenResponse;
import com.pfe.keycloak.dto.SignUpDto;
import com.pfe.keycloak.model.Employee;
import com.pfe.keycloak.repository.EmployeeRepository;
import com.pfe.keycloak.service.AuthService;
import jakarta.annotation.PostConstruct;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private WebClient webClient;
    private final WebClient.Builder webClientBuilder;
    private final String tokenUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-sercret}")
    private String clientSecret;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender emailSender;

    public AuthServiceImpl(WebClient.Builder webClientBuilder, @Value("${keycloak.token-url}") String tokenUrl) {
        this.webClientBuilder = webClientBuilder;
        this.tokenUrl = tokenUrl;
    }

    @PostConstruct
    public void init() {
        this.webClient = this.webClientBuilder.baseUrl(this.tokenUrl).build();
    }

    public Mono<String> login(String username, String password) {
        return webClient.post().uri(tokenUrl)
                .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("client_id", "employee")
                        .with("client_secret", "bFnv64BYcBpkoGGX0rWbCpup0c5yy6Ba")
                        .with("username", username)
                        .with("password", password))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("{\"error\": \"invalid_grant\", \"error_description\": \"Invalid user credentials\"}"));
    }

    @Override
    public ResponseEntity<?> signUp(SignUpDto signUpDto) {
        LOGGER.info("signUp... {}", signUpDto);
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm("employee")
                .clientId("employee")
                .clientSecret("bFnv64BYcBpkoGGX0rWbCpup0c5yy6Ba")
                .username(adminUsername)
                .password(adminPassword)
                .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                .build();

        keycloak.tokenManager().getAccessToken();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(signUpDto.getUsername());
        user.setFirstName(signUpDto.getFirstname());
        user.setLastName(signUpDto.getLastname());
        user.setEmail(signUpDto.getEmail());

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);

            LOGGER.info("Created userId {}", userId);

            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(signUpDto.getPassword());

            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCred);

            // Fetch the client by clientId ("employee")
            ClientRepresentation client = realmResource.clients().findByClientId("employee").get(0);
            // Assuming the client is found, fetch the HR role
            RoleRepresentation hrRole = realmResource.clients().get(client.getId()).roles().get("HR").toRepresentation();
            // Assign the HR role to the user
            userResource.roles().clientLevel(client.getId()).add(Collections.singletonList(hrRole));

            Employee employee = new Employee(signUpDto);
            employeeRepository.save(employee);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error registering user", HttpStatus.valueOf(response.getStatus()));
        }
    }

    public Mono<String> obtainAdminToken() {
        return webClient.post().uri("http://localhost:8080/realms/employee/protocol/openid-connect/token")
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("client_id", "employee")
                        .with("client_secret", "bFnv64BYcBpkoGGX0rWbCpup0c5yy6Ba"))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(TokenResponse::getAccess_token);
    }

    public List<UserRepresentation> getAllUsers() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm("employee")
                .clientId("employee")
                .clientSecret("bFnv64BYcBpkoGGX0rWbCpup0c5yy6Ba")
                .username(adminUsername)
                .password(adminPassword)
                .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                .build();

        keycloak.tokenManager().getAccessToken();

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        return usersResource.list();
    }

    public ResponseEntity<?> resetPassword(String username, String newPassword) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm("employee")
                    .clientId("employee")
                    .clientSecret("bFnv64BYcBpkoGGX0rWbCpup0c5yy6Ba")
                    .username(adminUsername)
                    .password(adminPassword)
                    .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                    .build();

            keycloak.tokenManager().getAccessToken();

            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(username, true);

            if (users.isEmpty()) {
                LOGGER.error("User not found with username: {}", username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with username: " + username);
            }

            UserRepresentation user = users.get(0);
            UserResource userResource = usersResource.get(user.getId());

            String userEmail = user.getEmail();
            if (userEmail == null || userEmail.isEmpty()) {
                LOGGER.error("No email found for user: {}", username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No email found for user: " + username);
            }

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(newPassword);
            credential.setTemporary(false);

            userResource.resetPassword(credential);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ahmed.benammar1@esprit.tn");
            message.setTo(userEmail);
            message.setSubject("Password Reset Notification");
            message.setText("Your password has been successfully reset. If you did not request this, please contact support immediately.");
            emailSender.send(message);

            LOGGER.info("Password reset successfully for user: {}", username);
            return ResponseEntity.ok("Password reset successfully for user: " + username);
        } catch (Exception e) {
            LOGGER.error("Error resetting password for user: {}, Error: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resetting password for user: " + username);
        }
    }

    public ResponseEntity<?> resetPasswordRequest(String username) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm("employee")
                    .clientId("employee")
                    .clientSecret("bFnv64BYcBpkoGGX0rWbCpup0c5yy6Ba")
                    .username(adminUsername)
                    .password(adminPassword)
                    .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                    .build();

            keycloak.tokenManager().getAccessToken();

            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(username, true);

            if (users.isEmpty()) {
                LOGGER.error("User not found with username: {}", username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with username: " + username);
            }

            UserRepresentation user = users.get(0);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(user.getEmail());
            message.setTo("ahmedbenammar01@gmail.com");
            message.setSubject("Password Reset Request");
            message.setText(username + " has requested a password reset. Please reset the password for this user.");
            emailSender.send(message);
            return ResponseEntity.ok("Password reset request sent successfully for user: " + username);
        } catch (Exception e) {
            LOGGER.error("Error sending password reset request for user: {}, Error: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resetting password for user: " + username);
        }
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> setPinForUser(String username, String pin) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(adminUsername)
                    .password(adminPassword)
                    .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                    .build();

            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> keycloakUsers = usersResource.search(username, true);

            if (keycloakUsers.isEmpty()) {
                throw new NotFoundException("User not found with username: " + username);
            }

            UserRepresentation user = keycloakUsers.get(0);
            UserResource userResource = usersResource.get(user.getId());

            String hashedPin = passwordEncoder.encode(pin);

            user.singleAttribute("hashedPin", hashedPin);
            userResource.update(user);

            return ResponseEntity.ok().body("PIN set successfully for user: " + username);
        } catch (Exception e) {
            LOGGER.error("Error setting PIN for user: {}, Error: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error setting PIN for user: " + username);
        }
    }

    public boolean checkPinForUser(String username, String pin) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm("employee")
                    .clientId("employee")
                    .clientSecret(clientSecret)
                    .username("adminUsername")
                    .password("adminPassword")
                    .build();

            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(username);

            if (users.isEmpty()) {
                throw new Exception("User not found");
            }

            UserRepresentation user = users.get(0);
            String hashedPin = user.getAttributes().get("hashedPin").get(0);
            return passwordEncoder.matches(pin, hashedPin);

        } catch (Exception e) {
            return false;
        }
    }
    public Boolean checkIfPinExist(String username) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authServerUrl)
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(adminUsername)
                    .password(adminPassword)
                    .build();

            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(username);

            if (users.isEmpty()) {
                throw new Exception("User not found");
            }

            UserRepresentation user = users.get(0);
            return user.getAttributes().containsKey("hashedPin");

        } catch (Exception e) {
            return false;
        }
    }
}
