package com.commerce.project.controller;

import com.commerce.project.security.jwt.JwtUtils;
import com.commerce.project.security.request.LoginRequest;
import com.commerce.project.security.request.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            // Step 1: Ask Spring Security to verify username + password
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Bad credentials");
        }

        // Step 2: Store authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Step 3: Get UserDetails from the authentication object
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Step 4: Generate JWT token
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        // Step 5: Extract roles as a list of strings
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Step 6: Return the token + user info
        return ResponseEntity.ok(new LoginResponse(jwtToken, userDetails.getUsername(), roles));
    }
}