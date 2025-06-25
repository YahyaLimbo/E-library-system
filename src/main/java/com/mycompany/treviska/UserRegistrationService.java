// UserRegistrationService.java
package com.mycompany.treviska;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User registerUser(User user) {
      if (userRepository.existsByUsername(user.getUsername()) || 
          userRepository.existsByEmail(user.getEmail())) {

          throw new ValidationException(
            "Username or Email already exists");
      }

      user.setPassword(passwordEncoder.encode(user.getPassword()));

      return userRepository.save(user);
  }
}
