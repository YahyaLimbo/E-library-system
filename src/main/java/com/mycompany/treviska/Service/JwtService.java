// JwtService.java
package com.mycompany.treviska.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import com.mycompany.treviska.Repository.UserRepository;
import com.mycompany.treviska.User;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String issuer;
    private final Duration ttl;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

   public String generateToken(final String username) {
        final var now = Instant.now();
        
        // Lookup user to get their role
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        // Create scope based on user role
        String scope = switch (user.getRole()) {
            case admin -> "ADMIN USER";
            case user -> "USER";
            default -> "USER";
        };
        
        System.out.println("=== JWT TOKEN GENERATION ===");
        System.out.println("User: " + username);
        System.out.println("Role in DB: " + user.getRole());
        System.out.println("Generated scope: " + scope);
        
        final var claimsSet = JwtClaimsSet.builder()
                .subject(username)
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(ttl))
                .claim("scope", scope)  // Set scope based on user role
                .claim("role", user.getRole().name()) // Also include original role
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }
}