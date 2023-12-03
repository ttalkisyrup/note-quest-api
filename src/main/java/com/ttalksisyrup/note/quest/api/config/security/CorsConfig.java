package com.ttalksisyrup.note.quest.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class CorsConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurity -> httpSecurity.configurationSource(cors()));
        return http.build();
    }

    @Bean
    protected UrlBasedCorsConfigurationSource cors() {
        CorsConfiguration configuration = new CorsConfiguration();
        var all = Collections.singletonList(CorsConfiguration.ALL);

        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://note-quest.deveun.com"));
        configuration.setAllowedHeaders(all);
        configuration.setAllowedMethods(all);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
