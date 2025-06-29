package com.mycompany.treviska;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialResponse {
    private Long materialid;
    private String title;
    private String author;
    private String identifier;
    private Material.MaterialType type;
    private String summary;
    private LocalDate datePublished;
    private String publisher;
    private String contributors;
    private Material.MaterialFormat format;
    private String language;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor from Material entity
    public MaterialResponse(Material material) {
        this.materialid = material.getMaterialid();
        this.title = material.getTitle();
        this.author = material.getAuthor();
        this.identifier = material.getIdentifier();
        this.type = material.getType();
        this.summary = material.getSummary();
        this.datePublished = material.getDatePublished();
        this.publisher = material.getPublisher();
        this.contributors = material.getContributors();
        this.format = material.getFormat();
        this.language = material.getLanguage();
        this.createdAt = material.getCreatedAt();
        this.updatedAt = material.getUpdatedAt();
    }
}