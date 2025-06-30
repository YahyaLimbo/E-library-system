
package com.mycompany.treviska;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
        
@Entity
@Table(name = "material_files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
               
public class MaterialFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //pk
    private Long id;
    
    @Column(name = "material_id", nullable = false) //fk to materialid(material table)
    private Long materialId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Column(name = "file_type")
    private String fileType; // MIME type like application/pdf
    
    
    @JdbcTypeCode(SqlTypes.VARBINARY)  // checked on postman, return 200 inclduing file opening
    @Column(name = "file_content")
    private byte[] fileContent;
    
    @Builder.Default
    @Column(name = "download_count")
     private Integer downloadCount = 0;
    @Builder.Default
    @Column(name = "is_primary")
    private Boolean isPrimary = false;
    
    @CreatedDate
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationship back to Material
    @ManyToOne(fetch = FetchType.LAZY) //review
    @JoinColumn(name = "material_id", insertable = false, updatable = false)
    private Material material;
    public MaterialFile(Long id, Long materialId, String name, Long fileSize, String fileType, 
                 Integer downloadCount, Boolean isPrimary, LocalDateTime uploadedAt, LocalDateTime updatedAt) {
    this.id = id;
    this.materialId = materialId;
    this.name = name;
    this.fileSize = fileSize;
    this.fileType = fileType;
    this.downloadCount = downloadCount;
    this.isPrimary = isPrimary;
    this.uploadedAt = uploadedAt;
    this.updatedAt = updatedAt;
    }
}