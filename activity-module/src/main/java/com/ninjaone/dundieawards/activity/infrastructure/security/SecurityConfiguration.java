package com.ninjaone.dundieawards.activity.infrastructure.security;

import com.ninjaone.dundieawards.common.infrastructure.security.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@Profile(value = {"dev", "prd"})
public class SecurityConfiguration {

    private final JwtTokenFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //@formatter:off
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/health/v1").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/openapi/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).access((request, auth) -> isLocalhost()) // Only allow from localhost
                        .requestMatchers(
                                "/", "/ui/**",
                                  "/*.html", "/*.js", "/*.css",
                                  "/*.svg", "/*.png", "/*.jpg", "/*.ico",
                                  "/assets/**",
                                  "/*.ttf",
                                  "/manifest.webmanifest"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable);

        //@formatter:on
        return httpSecurity.build();
    }

    private AuthorizationDecision isLocalhost() {
        final var httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        if( httpRequest != null) {
            final var allowedAddresses = Arrays.asList(
                    "127.0.0.1",
                    "0:0:0:0:0:0:0:1"
            );
            return new AuthorizationDecision(allowedAddresses.contains(
                    httpRequest.getRequest().getRemoteAddr())
            );
        }

        return new AuthorizationDecision(false);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://dundieawards.com"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
