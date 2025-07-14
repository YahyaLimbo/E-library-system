package com.mycompany.treviska.Service;

import com.mycompany.treviska.Repository.UserRepository;
import com.mycompany.treviska.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.GONE;

@ExtendWith(MockitoExtension.class) //extend mockito framework to junit
class UserServiceTest {
    @Mock
    
    private UserRepository userRepository; 
    
    @InjectMocks //autoInjection
    private UserService userService;
    
    private User userTest;
    @ExtendWith(MockitoExtension.class) 
    @BeforeEach
    void setup(){
        userTest = new User(); //why did i redefined as User UserTest = new User()?
        userTest.setUsername("TestUser");
        userTest.setPassword("testpassword123");
        userTest.setRole(User.UserRole.user);
        userTest.setEmail("test@example.com");

    
    }
    
    @Test
    void TestGetUserByUsernameExisting(){
        String userString="TestUser";
        when(userRepository.findByUsername(userString))
            .thenReturn(Optional.of(userTest));

        User result= userService.getUserByUsername(userString);
        
        
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(userString);
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getRole()).isEqualTo(User.UserRole.user);
        
        verify(userRepository, times(1)).findByUsername(userString);

    
    }
    @Test
    void TestGetUserByUsernameNotExisting() {
        String userString="WrongUsername";
        when(userRepository.findByUsername(userString))
            .thenReturn(Optional.empty());
        
        assertThatThrownBy(()->{
        userService.getUserByUsername(userString);}).isInstanceOf(ResponseStatusException.class)
                .satisfies(exception->{
                ResponseStatusException example=(ResponseStatusException) exception;
                assertThat(example.getStatusCode()).isEqualTo(GONE);
                });
        verify(userRepository, times(1)).findByUsername(userString);
    }
    @Test
    void TestGetUserByUsernameNull() {
    String ExampleNull = null;
    when(userRepository.findByUsername(ExampleNull))
            .thenReturn(Optional.empty());
    assertThatThrownBy(()->{
        userService.getUserByUsername(ExampleNull);
    })
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("deleted or inactivated");
    verify(userRepository, times(1)).findByUsername(ExampleNull);
    }
    //registration features
    @ExtendWith(MockitoExtension.class) 
    class UserRegistrationTest(){
        @Mock
        private UserRepository repository;
        @Mock
        private PasswordEncoder passwordEncoder;
        @InjectMocks
        private UserRegistrationService userRegistrationService;
        
        @Test
        void CredentialRegistrationTest(){
        }
        @Test
        void CheckDefaultRole(){
        }
        @Test
        void CheckDuplicateEmail(){
        }
        @Test
        void CheckDuplicateUsername(){
        }
    }
    //Jwt token testing and authentication
    @ExtendWith(MockitoExtension.class)
    class JwtTokenAuthenticationTest() {
        @Mock
        private AuthenticationManager authenticationManager;
        @Mock 
        private JwtService jwtService;
        @InjectMocks
        private AuthenticationService authenticationService;
        
        @Test
        void TokenLengthTest() {}
         @Test
    void testAuthenticate_WithValidCredentials_ShouldReturnToken() {
        // Test successful login
    }
    
    @Test
    void testAuthenticate_WithInvalidCredentials_ShouldThrowException() {
        // Test failed login
    }
        
    
    }
    
}
