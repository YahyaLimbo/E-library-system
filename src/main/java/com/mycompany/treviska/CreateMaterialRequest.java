package com.mycompany.treviska;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaterialRequest {
    
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;
    
    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author must not exceed 50 characters")
    private String author;
    
    @NotBlank(message = "Identifier is required")
    @Size(max = 50, message = "Identifier must not exceed 50 characters")
    private String identifier;
    
    @NotNull(message = "Material type is required")
    private Material.MaterialType type;
    
    @NotBlank(message = "Summary is required")
    private String summary;
    
    @NotNull(message = "Publication date is required")
    private LocalDate datePublished;
    
    @NotBlank(message = "Publisher is required")
    @Size(max = 50, message = "Publisher must not exceed 50 characters")
    private String publisher;
    
    @Size(max = 100, message = "Contributors must not exceed 100 characters")
    private String contributors;
    
    @NotNull(message = "Format is required")
    private Material.MaterialFormat format;
    
    @NotBlank(message = "Language is required")
    @Size(max = 20, message = "Language must not exceed 20 characters")
    private String language;
}