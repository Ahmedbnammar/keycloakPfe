package com.pfe.keycloak.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String username;
    private String email;
    private String password;
    private String accountStatus;
}
