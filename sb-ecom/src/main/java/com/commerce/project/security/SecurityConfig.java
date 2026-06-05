package com.commerce.project.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import javax.sql.DataSource;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain h2ConsoleFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(PathRequest.toH2Console())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );
        return http.build();
    }

    @Bean
    @Order(2)
//            .httpBasic() -"Use the header" so basic auth type of authentication
//            .sessionManagement(STATELESS)- "Don't remember me at all"
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/public/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    // BEAN 1: Just creates the JdbcUserDetailsManager — no DB queries here
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    // BEAN 2: Runs AFTER full startup (schema.sql has already run, tables exist)
    @Bean
    public CommandLineRunner initData(UserDetailsService userDetailsService) {
        return args -> {
            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;

            if (!manager.userExists("user1")) {
                UserDetails user1 = User.withUsername("user1")
                        .password(passwordEncoder().encode("password1"))
                        .roles("USER")
                        .build();
                manager.createUser(user1);
            }

            if (!manager.userExists("admin")) {
                UserDetails admin = User.withUsername("admin")
                        .password(passwordEncoder().encode("password2"))
                        .roles("ADMIN")
                        .build();
                manager.createUser(admin);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


