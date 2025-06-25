// RegistrationRequestDto.java
package com.mycompany.treviska;

public record RegistrationRequestDto(
        String username,
        String email,
        String password
) {
}
