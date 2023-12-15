package com.example.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// Importaciones necesarias para CORS
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf ->
                csrf
                .disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authRequest ->
              authRequest
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                )
            .sessionManagement(sessionManager ->
                sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    // Método para configurar CORS
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // 1. Especifica el origen permitido (tu aplicación React)
    configuration.setAllowedOrigins(Arrays.asList("https://nexus-notes.up.railway.app", "http://localhost:5173")); 

    // 2. Especifica los métodos HTTP permitidos
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

    // 3. Permite credenciales, lo que significa que se permite el intercambio de cookies entre el cliente y el servidor
    configuration.setAllowCredentials(true);

    // 4. Especifica los encabezados permitidos
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

    // 5. (Opcional) Especifica los encabezados expuestos
    configuration.setExposedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    
    // 6. Registra la configuración de CORS para todas las rutas
    source.registerCorsConfiguration("/**", configuration);
    
    return source;
}

}
