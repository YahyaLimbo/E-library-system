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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iduser;

     @Column(name = "usernam", nullable = false, unique = true)  // 💡 map to DB column name
    private String username;
    @Column(name = "email", nullable = false, unique = true)  // 💡 map to DB column name@Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}
