package com.shreyas.spring_boot_advanced_template.config.security;

import com.shreyas.spring_boot_advanced_template.service.implementations.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsServiceConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserServicesImpl userService;

    public UserDetailsServiceConfig(PasswordEncoder passwordEncoder, UserServicesImpl user) {
        this.passwordEncoder = passwordEncoder;
        this.userService = user;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
