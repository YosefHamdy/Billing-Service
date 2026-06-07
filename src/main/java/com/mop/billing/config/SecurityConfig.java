package com.mop.billing.config;

import com.mop.billing.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService      userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ── Disable CSRF — stateless REST API ──────────────────────────
            .csrf(AbstractHttpConfigurer::disable)

            // ── No server-side sessions ─────────────────────────────────────
            .sessionManagement(s ->
                    s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // ── Allow H2 console frames in dev ──────────────────────────────
            .headers(h -> h.frameOptions(f -> f.sameOrigin()))

            // ── Authorization rules ─────────────────────────────────────────
            .authorizeHttpRequests(auth -> auth

                // Sadad inbound SOAP callbacks — secured by XML signature
                .requestMatchers("/sadad/**").permitAll()

                // H2 console — dev only, remove in production
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                // Actuator health — useful behind a load balancer
                .requestMatchers("/actuator/health").permitAll()

                // Bill creation and cancellation — employee only

                .requestMatchers(HttpMethod.POST,   "/api/bills/generate").permitAll()
                .requestMatchers(HttpMethod.DELETE,  "/api/bills/*/cancel").hasAnyRole("EMPLOYEE","ADMIN")

                // Admin-only actions
                .requestMatchers("/api/bills/admin/**").hasRole("ADMIN")

                // Bill queries — employee or investor
                .requestMatchers(HttpMethod.GET, "/api/bills/**")
                        .hasAnyRole("EMPLOYEE","ADMIN","INVESTOR")

                // All other API calls require authentication
                .requestMatchers("/api/**").authenticated()

                // Everything else — deny
                .anyRequest().denyAll()
            )

            // ── Authentication provider ─────────────────────────────────────
            .authenticationProvider(authenticationProvider())

            // ── JWT filter — runs before Spring's username/password filter ──
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
