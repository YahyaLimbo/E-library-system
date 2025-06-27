package com.mycompany.treviska;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaterialRequest {
    
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;
    
    @Size(max = 50, message = "Author must not exceed 50 characters")
    private String author;
    
    private Material.MaterialType type;
    
    private String summary;
    
    private LocalDate datePublished;
    
    @Size(max = 50, message = "Publisher must not exceed 50 characters")
    private String publisher;
    
    @Size(max = 100, message = "Contributors must not exceed 100 characters")
    private String contributors;
    
    private Material.MaterialFormat format;
    
    @Size(max = 20, message = "Language must not exceed 20 characters")
    private String language;
}