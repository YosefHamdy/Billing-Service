package com.mop.billing.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Security Layer — JWT filter.
 *
 * Runs once per request. Extracts the Bearer token from the Authorization header,
 * validates it, and populates the SecurityContext so Spring Security
 * can apply @PreAuthorize rules on controllers.
 *
 * Requests to the Sadad SOAP endpoints (/sadad/**) bypass this filter entirely —
 * they are secured by XML signature validation in their own controllers.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil            jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest  request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain         filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // No token present — pass through (public endpoints or Sadad callbacks)
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt      = authHeader.substring(BEARER_PREFIX.length());
        final String username;

        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            log.warn("[JwtFilter] Could not extract username from token: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // Already authenticated in this request cycle
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Load user and validate
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtUtil.isTokenValid(jwt, userDetails)) {
            log.warn("[JwtFilter] Invalid token for user={}", username);
            filterChain.doFilter(request, response);
            return;
        }

        // Build authorities from the token's roles claim (overrides DB lookup for stateless)
        List<SimpleGrantedAuthority> authorities = jwtUtil.extractRoles(jwt).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.debug("[JwtFilter] Authenticated user={} roles={}", username, authorities);

        filterChain.doFilter(request, response);
    }

    /**
     * Skip JWT validation entirely for Sadad SOAP callback endpoints.
     * These are secured by XML digital signature at the controller level.
     */
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/sadad/");
    }
}
