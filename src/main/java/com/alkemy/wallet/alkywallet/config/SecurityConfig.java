package com.alkemy.wallet.alkywallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) Desactivar CSRF (para no necesitar token)
                .csrf(csrf -> csrf.disable())

                // 2) Permitir cualquier método (GET, POST, PUT, DELETE) bajo /api/**
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 3) Habilitar HTTP Basic para que tome tu user:1234 aunque no se requiera
                .httpBasic(withDefaults());

        return http.build();
    }
}
