package com.mycompany.treviska;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
// or for newer versions:
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestFileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", columnDefinition = "BYTEA")
    private byte[] content;
}