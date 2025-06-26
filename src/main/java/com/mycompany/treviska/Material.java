package com.mycompany.treviska;

import jakarta.persistence*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditEntityListener;


import java.time.LocalDateTime;

@Entity
@Table(name="material")
@Getter @Setter @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="materialid")
    private long materialid;
    
    @Column(name ="title", nullable = false, length=255)
    private String title;
    
    
    @Column(name ="author", length = 50)
    private String author;
    
    @Column(name ="identifier",unique= true, length = 20)
    private String identifier; 
    
    @Column(name ="type", nullable= false, length=50)
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;
    
    @Column(name ="summary", columnDefinition ="TEXT")
    private String summary;
    
    //convert this later from integer to date( will need to have day/month/year.
    @Column(name ="date_published", nullable= false) 
    private LocalDate date_published;
    
    @Column(name ="publisher", nullable = false, length =50)
    private String publisher;
    
    
    @Column(name ="contributors", length = 100)
    private String contributors;
    
    @Column(name ="format", length =50, nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialFormat format;
   
    @Column(name ="language", length = 255)
    private String language;
    
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    // Enums
    public enum MaterialType {
        BOOK, JOURNAL, MAGAZINE, NEWSPAPER, DVD, AUDIO_BOOK, E_BOOK, REFERENCE
    }
     public enum MaterialFormat {
        print, digital, audiobook
    }
    
    // Helper method to check if material is available
    public boolean isAvailable() {
        return true; // Since you don't have availability status in your current schema
    }
}
    
    
    
    
    
    
    
    
    
    



}
