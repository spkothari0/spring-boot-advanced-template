package com.shreyas.spring_boot_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserDetailsServiceConfig {
    private final DataSource dataSource;

    @Autowired
    public UserDetailsServiceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    // before this once run: Create user schema present in user.sql
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        if (!userExists(jdbcUserDetailsManager, "shreyas")) {
            UserDetails user = User.withUsername("shreyas")
                    .password(passwordEncoder.encode("123"))
                    .roles("USER")
                    .build();
            jdbcUserDetailsManager.createUser(user);
        }

        if (!userExists(jdbcUserDetailsManager, "admin")) {
            UserDetails user = User.withUsername("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("ADMIN")
                    .build();
            jdbcUserDetailsManager.createUser(user);
        }

        return jdbcUserDetailsManager;
    }

    private boolean userExists(JdbcUserDetailsManager jdbcUserDetailsManager, String username) {
        try {
            jdbcUserDetailsManager.loadUserByUsername(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }
}
