
package com.mycompany.treviska.Controller;

import com.mycompany.treviska.security.AuthenticationRequestDto;
import com.mycompany.treviska.security.AuthenticationResponseDto;
import com.mycompany.treviska.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
      @RequestBody final AuthenticationRequestDto authenticationRequestDto
    ) {
        return ResponseEntity.ok(
          authenticationService.authenticate(authenticationRequestDto));
    }
}
