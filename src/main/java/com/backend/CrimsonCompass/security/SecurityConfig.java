package com.backend.CrimsonCompass.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtProperties jwtProps;
    private final com.backend.CrimsonCompass.config.SecurityProperties securityProps;

    @org.springframework.beans.factory.annotation.Value("${cors.allowed-origins}")
    private String allowedOrigins;

    public SecurityConfig(JwtProperties jwtProps, com.backend.CrimsonCompass.config.SecurityProperties securityProps) {
        this.jwtProps = jwtProps;
        this.securityProps = securityProps;
    }

    @Bean
    public SecretKey jwtSecretKey() {
        // derive a SecretKey from the raw string
        return Keys.hmacShaKeyFor(jwtProps.getSecret().getBytes());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())           // disable CSRF for stateless JWT
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(a -> a
                        // 1) Only allow sync from configured IPs
                        .requestMatchers(HttpMethod.POST, "/api/users/sync")
                        .access((auth, ctx) -> {
                            String remoteAddr = ctx.getRequest().getRemoteAddr();
                            boolean allowed = securityProps.getAllowedIps() != null && securityProps.getAllowedIps().contains(remoteAddr);
                            return new AuthorizationDecision(allowed);
                        })
                        // 2) Health endpoint (if you add actuator)
                        .requestMatchers("/actuator/health").permitAll()
                        // 3) Everything else under /api
                        .requestMatchers("/api/**").permitAll()
                        // 4) All other routes need auth
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        if ("*".equals(allowedOrigins)) {
             cfg.addAllowedOriginPattern("*");
        } else {
             // Split by comma if multiple origins
             for (String origin : allowedOrigins.split(",")) {
                 cfg.addAllowedOrigin(origin.trim());
             }
        }
        cfg.addAllowedMethod("*");
        cfg.addAllowedHeader("*");
        cfg.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}


