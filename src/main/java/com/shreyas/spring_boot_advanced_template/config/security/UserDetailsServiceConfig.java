package com.shreyas.spring_boot_advanced_template.config.security;

import com.shreyas.spring_boot_advanced_template.repository.interfaces.IUserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsServiceConfig {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepo userRepo;

    public UserDetailsServiceConfig(PasswordEncoder passwordEncoder, IUserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Bean
    public UserDetailsService userDetails(){
        return username -> userRepo.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found !"));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetails());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
