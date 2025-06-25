package com.mycompany.treviska;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;

@Configuration
@Getter
@Setter
public class JwtConfig {

    @Value("${jwt.private-key-location}")
    private Resource privateKeyResource;

    @Value("${jwt.public-key-location}")
    private Resource publicKeyResource;

    @Value("${jwt.ttl}")
    private Duration ttl;

    @Value("${spring.application.name}")
    private String appName;

    private PublicKey getPublicKey() throws Exception {
        try (InputStream input = publicKeyResource.getInputStream()) {
            byte[] keyBytes = input.readAllBytes();
            String pubKey = new String(keyBytes)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(pubKey);
            return KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
        }
    }

    private PrivateKey getPrivateKey() throws Exception {
        try (InputStream input = privateKeyResource.getInputStream()) {
            byte[] keyBytes = input.readAllBytes();
            String privKey = new String(keyBytes)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(privKey);
            return KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        }
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        RSAKey jwk = new RSAKey.Builder((java.security.interfaces.RSAPublicKey) getPublicKey())
                .privateKey((java.security.interfaces.RSAPrivateKey) getPrivateKey())
                .build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey((java.security.interfaces.RSAPublicKey) getPublicKey()).build();
    }

    @Bean
    public JwtService jwtService(JwtEncoder jwtEncoder) {
        return new JwtService(appName, ttl, jwtEncoder);
    }
}
