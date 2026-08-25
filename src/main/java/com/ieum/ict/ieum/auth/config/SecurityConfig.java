package com.ieum.ict.ieum.auth.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import com.ieum.ict.ieum.auth.security.GoogleOAuth2SuccessHandler;
import com.ieum.ict.ieum.auth.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
    private final ObjectProvider<ClientRegistrationRepository> clientRegistrationRepository;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler,
                          ObjectProvider<ClientRegistrationRepository> clientRegistrationRepository) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.googleOAuth2SuccessHandler = googleOAuth2SuccessHandler;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @org.springframework.context.annotation.Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/error", "/auth/**", "/oauth2/**", "/login/**", "/api/health", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated());

        if (clientRegistrationRepository.getIfAvailable() != null) {
            http.oauth2Login(oauth2 -> oauth2.successHandler(googleOAuth2SuccessHandler));
        }

        return http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
