package com.commerce.project.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        logger.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());

        try {
            // Step 1: Pull JWT out of the Authorization header
            String jwt = jwtUtils.getJwtFromHeader(request);

            // Step 2: If token exists and is valid
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                // Step 3: Get username from the token
                String username = jwtUtils.getUsernameFromJwtToken(jwt);

                // Step 4: Load full user details (roles, etc.) from DB using username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Step 5: Build an authentication object with user + roles
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                          // no credentials needed (token already verified)
                                userDetails.getAuthorities()   // roles
                        );

                // Step 6: Attach request details (IP, session info) to the auth object
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Step 7: Tell Spring Security "this request is authenticated"
                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.debug("Roles from JWT: {}", userDetails.getAuthorities());
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        // Step 8: Hand control to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}