// UserService.java
package com.mycompany.treviska.Service;

import com.mycompany.treviska.User;
import com.mycompany.treviska.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.GONE;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username)
          .orElseThrow(() -> new ResponseStatusException(GONE, 
              "The user account has been deleted or inactivated"));
    }
}
