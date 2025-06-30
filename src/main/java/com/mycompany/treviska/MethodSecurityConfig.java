package com.mycompany.treviska.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(
    prePostEnabled = true,  
    securedEnabled = true,  // Enable @Secured
    jsr250Enabled = true   
)
public class MethodSecurityConfig {
    
}