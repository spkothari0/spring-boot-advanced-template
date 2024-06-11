package com.shreyas.spring_boot_demo.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";

    final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//        http.formLogin(withDefaults());
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean             // before this once run: Create user schema present in user.sql
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager jdbcUserMgr = new JdbcUserDetailsManager(dataSource);

        if(!userExists(jdbcUserMgr,"shreyas")) {
            UserDetails user1= User.withUsername("shreyas").password(passwordEncoder().encode("123")).roles("USER").build();
            jdbcUserMgr.createUser(user1);
        }
        if(!userExists(jdbcUserMgr,"admin")) {
            UserDetails user2 = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
            jdbcUserMgr.createUser(user2);
        }

        return jdbcUserMgr;
//        return new InMemoryUserDetailsManager(user1, user2);
    }

    private boolean userExists(JdbcUserDetailsManager jdbcUserDetailsManager, String username) {
        try {
            jdbcUserDetailsManager.loadUserByUsername(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
