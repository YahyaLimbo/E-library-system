// RegistrationRequestDto.java
package com.mycompany.treviska.security;

public record RegistrationRequestDto(
        String username,
        String email,
        String password
) {
}
