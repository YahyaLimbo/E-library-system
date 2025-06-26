// UserMapper.java
package com.mycompany.treviska.security;

import com.mycompany.treviska.User;
import com.mycompany.treviska.security.UserProfileDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserProfileDto toUserProfileDto(final User user) {
        return new UserProfileDto(user.getEmail(), user.getUsername());
    }
}
