package com.TaskManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@Configuration
public class SecurityConfig{
    //  @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf().disable() // Disable CSRF protection
    //         .authorizeRequests()
    //             .anyRequest().authenticated() // Adjust based on your requirements
    //             .and()
    //         .formLogin(); // or another authentication method

    //     return http.build();
    // }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
