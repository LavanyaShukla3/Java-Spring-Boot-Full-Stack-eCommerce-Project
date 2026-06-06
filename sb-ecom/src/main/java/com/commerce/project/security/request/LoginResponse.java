package com.commerce.project.security.request;

import java.util.List;

public class LoginResponse {
    private String jwtToken;
    private String username;
    private List<String> roles;

    public LoginResponse(String jwtToken, String username, List<String> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
    }

    public String getJwtToken() { return jwtToken; }
    public String getUsername() { return username; }
    public List<String> getRoles() { return roles; }
}