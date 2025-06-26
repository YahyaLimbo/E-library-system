// RegistrationController.java
package com.mycompany.treviska.Controller;

import com.mycompany.treviska.security.RegistrationMapper;
import com.mycompany.treviska.security.RegistrationRequestDto;
import com.mycompany.treviska.security.RegistrationResponseDto;
import com.mycompany.treviska.Service.UserRegistrationService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UserRegistrationService userRegistrationService;

    private final RegistrationMapper userRegistrationMapper;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(
      @Valid @RequestBody final RegistrationRequestDto registrationDTO) {

        final var registeredUser = userRegistrationService
          .registerUser(userRegistrationMapper.toEntity(registrationDTO));

        return ResponseEntity.ok(
          userRegistrationMapper.toRegistrationResponseDto(registeredUser)
        );
    }

}
