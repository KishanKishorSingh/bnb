package com.airbnb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class BnbConfiguration {
//    private JWTFilter jwtFilter;
//
//    public BnbConfiguration(JWTFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().cors().disable();
    http.authorizeHttpRequests().anyRequest().permitAll();
//     http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
//     http.authorizeHttpRequests()
//                .requestMatchers("/app/v1/airbnb/createUser","/app/v1/airbnb/ownerUser").permitAll()
//                .requestMatchers("/app/v1/airbnb/propertyManager").hasRole("ADMIN")
//                .anyRequest().authenticated();

    return http.build();
}
}
