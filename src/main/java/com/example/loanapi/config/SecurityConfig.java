package com.example.loanapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Configure the in-memory users and their roles
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build(),
            User.withUsername("customer1")
                .password(passwordEncoder().encode("custpass1"))
                .roles("CUSTOMER")
                .build(),
            User.withUsername("customer2")
                .password(passwordEncoder().encode("custpass2"))
                .roles("CUSTOMER")
                .build()
        );
    }

    // Define the password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure the HTTP security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**").permitAll() // Allow H2 Console access
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
            .anyRequest().authenticated()
        )
        .headers(headers -> headers.frameOptions().disable()) // Required for H2 Console
        .httpBasic();;
            

        return http.build();
    }

    // Expose the AuthenticationManager as a Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
