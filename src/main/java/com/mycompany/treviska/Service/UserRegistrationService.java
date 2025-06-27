
package com.mycompany.treviska.Service;

import com.mycompany.treviska.Repository.UserRepository;
import com.mycompany.treviska.User;
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
      
      // Set default role if not specified
      if (user.getRole() == null) {
          user.setRole(User.UserRole.user);
      }
      return userRepository.save(user);
  }
}
