// UserRegistrationMapper.java
package com.mycompany.treviska;

import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    public User toEntity(RegistrationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setEmail(registrationRequestDto.email());
        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());

        return user;
    }

    public RegistrationResponseDto toRegistrationResponseDto(
      final User user) {

        return new RegistrationResponseDto(
          user.getEmail(), user.getUsername());
    }

}
