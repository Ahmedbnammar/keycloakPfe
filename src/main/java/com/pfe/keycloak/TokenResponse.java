package com.pfe.keycloak;

public class TokenResponse {
    private String access_token;
    // Add other fields if needed, like token_type, refresh_token, etc.

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    // If you add more fields, include getters and setters for them as well
}
