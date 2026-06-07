package com.mop.billing.security;

import com.mop.billing.data.entity.User;
import com.mop.billing.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Security Layer — UserDetailsService.
 *
 * Loads users from USER_DATA table. Faithfully ports the original
 * com.project.security.UserDetailsServiceImpl role-assignment logic:
 *   - Investor  → ROLE_INVESTOR, ROLE_COMPLEXES
 *   - Employee with no roles → ROLE_EMPLOYEE
 *   - Employee with roles    → roles from ROLE_DESC via USER_ROLE
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = buildAuthorities(user);

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                user.isActive(),          // enabled
                true,                     // accountNonExpired
                true,                     // credentialsNonExpired
                user.isAccountNonLocked(),// accountNonLocked
                authorities
        );
    }

    private List<GrantedAuthority> buildAuthorities(User user) {
        // Investor — mirrors original UserDetailsServiceImpl exactly
        if ("Y".equalsIgnoreCase(user.getIsInvestor())) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_INVESTOR"),
                    new SimpleGrantedAuthority("ROLE_COMPLEXES")
            );
        }

        // Employee with no custom roles → default ROLE_EMPLOYEE
        if (CollectionUtils.isEmpty(user.getRoles())) {
            return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }

        // Employee with custom roles from ROLE_DESC
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return new ArrayList<>(authorities);
    }
}
