package com.mycompany.treviska;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialSearchRequest {
    private String title;
    private String author;
    private String publisher;
    private Material.MaterialType type;
    private Material.MaterialFormat format;
    private String language;
    private Integer publicationYear;
    private String keyword; // For searching in summary/description
}