package com.mycompany.treviska;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="users")
@Getter @Setter @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userid")
    private Long userid;

    @Column(name = "username", nullable = false, unique = true, length = 20)  
    private String username;
    @Column(name = "email", nullable = false, unique = true, length= 30) 
    private String email;

    @Column(name = "password", nullable = false, length=255)
    private String password;
    @Column(name = "role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.user;  
    
    public enum UserRole {
        admin, user
    }
}
