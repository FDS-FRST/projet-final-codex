package org.foodshare.api.dto;

public class JwtResponse {
    private String token;
    private String email;
    private String role;

    public JwtResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }
    // getters
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}