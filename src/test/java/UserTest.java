import com.mycompany.treviska.Repository.UserRepository;
import com.mycompany.treviska.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import com.mycompany.treviska.Treviska; 
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.BeforeEach;

//handles login prompts
@DataJpaTest 
@ContextConfiguration(classes = Treviska.class)
public class UserTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;
    private User userTest;
    @BeforeEach
     void Credentials() {
        userTest = new User(); //why did i redefined as User UserTest = new User()?
        userTest.setUsername("TestUser");
        userTest.setPassword("testpassword123");
        userTest.setRole(User.UserRole.user);
        userTest.setEmail("test@example.com");
     }
     
     //UNIT TEST CASE TO CHECK RIGHT CREDENTIALS
    @Test
    void CredentialsName() {
        String ExistingUsername = "Michael";
        userTest.setUsername(ExistingUsername);
        entityManager.persistAndFlush(userTest);
        Optional<User> found = userRepository.findByUsername(ExistingUsername);
        
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo(ExistingUsername);
    }
    @Test
    void CredentialsPassword() {
        String ExistingPassword = "HelloWorld";
        userTest.setPassword(ExistingPassword);
        entityManager.persistAndFlush(userTest);
        Optional<User> found = userRepository.findByPassword(ExistingPassword);
        
        assertThat(found).isPresent();
        assertThat(found.get().getPassword()).isEqualTo(ExistingPassword);
    }
    @Test
    void CredentialsEmail() {
        String ExistingEmail = "helloworld@gmail.com";
        userTest.setEmail(ExistingEmail);
        entityManager.persistAndFlush(userTest);
        Optional<User> found = userRepository.findByEmail(ExistingEmail);
        
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(ExistingEmail);
    }
    @Test
    void CredentialRole() {
        User.UserRole ExistingRole = User.UserRole.user;
        userTest.setRole(ExistingRole);
        entityManager.persistAndFlush(userTest);
        Optional<User> found = userRepository.findByRole(ExistingRole);
        
        assertThat(found).isPresent();
        assertThat(found.get().getRole()).isEqualTo(ExistingRole);
    }
    //unit test for not passing crederntials
    @Test
    void WrongCredentialsEmail() {
        String WrongExistingEmail = "helloworld@gmail.com";
        userTest.setEmail(WrongExistingEmail);
        entityManager.persistAndFlush(userTest);
        Optional<User> notFound = userRepository.findByEmail("thisIsNotAnEmail@gmail.com");
        
        assertThat(notFound).isEmpty();
    }
    //unit test for SQLinjections, it is hard sql injections to access db if not using @Query with concatenation
    @Test
    void SqlInjectionTest() {
        entityManager.persistAndFlush(userTest);
        String SqlStmtInjection = "user' OR '1'='1'--";
        Optional<User> result = userRepository.findByUsername(SqlStmtInjection);
        
        assertThat(result).isEmpty();
    }
    @Test
    void CheckUniqueUsername() {
        String TestExistingUsername = "Michael";
        userTest.setUsername(TestExistingUsername);
        entityManager.persistAndFlush(userTest);  
        //duplicateEntity cannot pass with one class object
        User DuplicateEntity = new User();
        DuplicateEntity.setUsername("Michael");
        DuplicateEntity.setPassword("temporarypasswrord");
        DuplicateEntity.setEmail("temporaryEmail");
        DuplicateEntity.setRole(User.UserRole.user);
        
        assertThatThrownBy(()->{
        entityManager.persistAndFlush(DuplicateEntity);}).isInstanceOf(Exception.class);
    }
    
    @Test
    void CheckUniqueEmail() {
        String TestExistingEmail = "test@gmail.com";
        userTest.setEmail(TestExistingEmail);
        entityManager.persistAndFlush(userTest);  
        
        User DuplicateEntity = new User();
        DuplicateEntity.setUsername("TemporaryUser");
        DuplicateEntity.setPassword("temporarypasswrord");
        DuplicateEntity.setEmail("test@gmail.com");
        DuplicateEntity.setRole(User.UserRole.user);
        
        assertThatThrownBy(()->{
        entityManager.persistAndFlush(DuplicateEntity);}).isInstanceOf(Exception.class);
    }
    //ai generated test cases-curious if AI tool can perform unit testin with complex sql injections
    @Test
void testSqlInjectionWithUnionAttack() {
    entityManager.persistAndFlush(userTest);
    
    // Try UNION-based SQL injection
    String maliciousInput = "test' UNION SELECT * FROM users WHERE '1'='1";
    
    Optional<User> result = userRepository.findByUsername(maliciousInput);
    
    // Should NOT work - JPA treats this as a literal username
    assertThat(result).isEmpty();
}

@Test
void testMultipleSingleQuotesInInput() {
    entityManager.persistAndFlush(userTest);
    
    // Test with multiple quotes (common injection technique)
    String inputWithQuotes = "test''';'''";
    
    Optional<User> result = userRepository.findByUsername(inputWithQuotes);
    
    // Should handle quotes safely
    assertThat(result).isEmpty();
}
@Test
void testNullValuesAreRejected() {
    User invalidUser = new User();
    // Don't set required fields
    
    // This should throw an exception for null required fields
    assertThatThrownBy(() -> {
        entityManager.persistAndFlush(invalidUser);
    }).isInstanceOf(Exception.class);
}


@Test
void testUsernameTooLong() {
    User invalidUser = new User();
    invalidUser.setUsername("ThisUsernameIsWayTooLongAndExceedsThe20CharacterLimit"); // > 20 chars
    invalidUser.setEmail("test@example.com");
    invalidUser.setPassword("password123");
    invalidUser.setRole(User.UserRole.user);
    
    // This should throw an exception
    assertThatThrownBy(() -> {
        entityManager.persistAndFlush(invalidUser);
    }).isInstanceOf(Exception.class);
}
}
