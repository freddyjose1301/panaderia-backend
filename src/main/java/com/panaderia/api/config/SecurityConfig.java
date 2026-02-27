package com.panaderia.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // <--- ESTO ES VITAL: Desactiva la protección contra ataques para pruebas
            .cors(Customizer.withDefaults()) // Habilita la configuración de CORS que hicimos
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite que todas las rutas sean accesibles por ahora
            );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Aquí definimos el acceso para el dueño que está en el extranjero
        UserDetails admin = User.builder()
            .username("admin")
            .password("{noop}panaderia2026") // Tu clave de acceso
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(admin);
    }
}