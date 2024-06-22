package com.shreyas.spring_boot_advanced_template.config.security;

import com.shreyas.spring_boot_advanced_template.service.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsServiceConfig {
//    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final UserServicesImpl userService;

    @Autowired
    public UserDetailsServiceConfig(PasswordEncoder passwordEncoder, UserServicesImpl user) {
//        this.dataSource = dataSource;
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


    // before this once run: Create user schema present in user.sql
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        if (!userExists(jdbcUserDetailsManager, "shreyas")) {
//            UserDetails user = User.withUsername("shreyas")
//                    .password(passwordEncoder.encode("123"))
//                    .roles("USER")
//                    .build();
//            jdbcUserDetailsManager.createUser(user);
//        }
//
//        if (!userExists(jdbcUserDetailsManager, "admin")) {
//            UserDetails user = User.withUsername("admin")
//                    .password(passwordEncoder.encode("admin"))
//                    .roles("ADMIN")
//                    .build();
//            jdbcUserDetailsManager.createUser(user);
//        }
//
//        return jdbcUserDetailsManager;
//    }

//    private boolean userExists(JdbcUserDetailsManager jdbcUserDetailsManager, String username) {
//        try {
//            jdbcUserDetailsManager.loadUserByUsername(username);
//            return true;
//        } catch (UsernameNotFoundException e) {
//            return false;
//        }
//    }
}
