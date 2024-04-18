package com.pfe.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Setter
public class SignUpDto {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Date birthDate;
    private String phoneNumber;
    private String gender;
    private int statusCode;
    private String statusMessage;
}