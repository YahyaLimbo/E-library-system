package com.mycompany.treviska.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Get authorities from the default converter (handles scope claim)
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        
        // Create a mutable set
        Set<GrantedAuthority> allAuthorities = new HashSet<>(authorities);
        
        // Also add role-based authorities if role claim exists
        String role = jwt.getClaimAsString("role");
        if (role != null) {
            allAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        }
        
        // Debug logging
        System.out.println("=== JWT AUTHORITIES DEBUG ===");
        System.out.println("Token subject: " + jwt.getSubject());
        System.out.println("Scope claim: " + jwt.getClaimAsString("scope"));
        System.out.println("Role claim: " + role);
        System.out.println("Final authorities: " + allAuthorities);
        
        return new JwtAuthenticationToken(jwt, allAuthorities);
    }
}