package com.mycompany.treviska;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * DTO for returning tag search results without Hibernate lazy loading issues
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagsResponseDto {
    private Long materialTagId;
    private Long materialId;
    private String materialType;
    private List<String> tags;
    private int tagCount;
    
    // Constructor from Tags entity
    public TagsResponseDto(Tags tags) {
        this.materialTagId = tags.getMaterialTagId();
        this.materialId = tags.getMaterialId();
        this.materialType = tags.getType();
        this.tags = tags.getTags();
        this.tagCount = tags.getTagCount();
    }
    
    // Static factory method
    public static TagsResponseDto fromEntity(Tags tags) {
        return new TagsResponseDto(tags);
    }
}