// JpaUserDetailsService.java
package com.mycompany.treviska.Service;

import com.mycompany.treviska.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) 
      throws UsernameNotFoundException {

        return userRepository.findByUsername(username).map(user -> {
            List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
            );
            
            return User.builder()
                    .username(username)
                    .password(user.getPassword())
                    .authorities(authorities)  // Add role-based authorities
                    .build();
        }).orElseThrow(() -> new UsernameNotFoundException(
            "User with username [%s] not found".formatted(username)));
    }

}
