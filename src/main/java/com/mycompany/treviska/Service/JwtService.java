// JwtService.java
package com.mycompany.treviska.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String issuer;
    private final Duration ttl;
    private final JwtEncoder jwtEncoder;

    public String generateToken(final String username) {
        final var now = Instant.now();
        
        final var claimsSet = JwtClaimsSet.builder()
                .subject(username)
                .issuer(issuer)
                .issuedAt(now)  // Add issued at claim
                .expiresAt(now.plus(ttl))
                .claim("scope", "USER")  // Add scope claim
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
          .getTokenValue();
    }
}