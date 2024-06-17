package com.shreyas.spring_boot_demo.config;

import com.shreyas.spring_boot_demo.filter.correlation.CorrelationIdFilter;
import com.shreyas.spring_boot_demo.jwt.AuthEntryPointJwt;
import com.shreyas.spring_boot_demo.jwt.AuthTokenFilter;
import com.shreyas.spring_boot_demo.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public SecurityConfig(AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, UserDetailsService userService) {
        return new AuthTokenFilter(jwtUtils, userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthTokenFilter authTokenFilter, CorrelationIdFilter correlationIdFilter) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v1/auth/**").permitAll() // Allow all requests to /api/v1/auth/**
                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll() // Allow access to Swagger UI
                        .requestMatchers("/actuator/**").hasRole("ADMIN") // Allow only users with the ADMIN role to access Actuator endpoints
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .headers(headers -> headers
                        .frameOptions(options ->
                                options.sameOrigin()
                        )
                )
                .csrf(csrf -> csrf.disable())
                // add our custom JWT security filter before the UsernamePasswordAuthenticationFilter
                // so that the JWT token can be added to the "SecurityContextHolder"
                // ALWAYS ADD OUR CUSTOM FILTER TO THE FILTER CHAIN
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(correlationIdFilter, AuthTokenFilter.class);
//                .addFilterAfter(cacheFilter, AuthTokenFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
