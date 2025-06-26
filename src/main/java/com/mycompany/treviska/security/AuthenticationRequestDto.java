// AuthenticationRequestDto.java
package com.mycompany.treviska.security;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}
