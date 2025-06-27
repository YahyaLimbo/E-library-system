package com.mycompany.treviska;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "material")
@Getter @Setter @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "materialid")
    private Long materialid;
    
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    
    @Column(name = "author", nullable = false, length = 50)
    private String author;
    
    @Column(name = "identifier", unique = true, nullable = false, length = 50)
    private String identifier;
    
    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MaterialType type;
    
    @Column(name = "summary", columnDefinition = "TEXT", nullable = false)
    private String summary;
    
    @Column(name = "date_published", nullable = false)
    private LocalDate datePublished;
    
    @Column(name = "publisher", nullable = false, length = 50)
    private String publisher;
    
    @Column(name = "contributors", length = 100)
    private String contributors;
    
    @Column(name = "format", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private MaterialFormat format;
    
    @Column(name = "language", nullable = false, length = 20)
    private String language;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums matching your database schema
    public enum MaterialType {
        book, journal, magazine, newspaper, researchpaper,
        Magazine, Thesis, ConferencePaper, Referencematerial,
        database, Archive, dataset, audioBook
    }
    
    public enum MaterialFormat {
        print, digital, audiobook
    }
    
    // Helper method to check if material is available
    public boolean isAvailable() {
        return true; // Since you don't have availability status in your current schema
    }
}