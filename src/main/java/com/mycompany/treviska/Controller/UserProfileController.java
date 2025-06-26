// UserProfileController.java
package com.mycompany.treviska.Controller;

import com.mycompany.treviska.Service.UserService;
import com.mycompany.treviska.security.UserMapper;
import com.mycompany.treviska.security.UserProfileDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getUserProfile(
      final Authentication authentication) {

        final var user = 
          userService.getUserByUsername(authentication.getName());

        return ResponseEntity.ok(userMapper.toUserProfileDto(user));
    }
}
