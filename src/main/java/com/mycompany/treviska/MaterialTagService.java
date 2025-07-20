package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MaterialTagService {
    private MaterialRepository materialRepository;
    private MaterialTagRepository materialTagRepository;
    private UserRepository userRepository;
    
    @PreAuthorize("hasAnyRole('SCOPE_ADMIN')")
    public MaterialTagsResponse createTags(Long userid, addTagsRequest request){
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ValidationException("Material not found with ID: " + materialId));
         if (tagsRepository.existsByMaterialId(materialId)) {
            throw new ValidationException("Tags already exist for material ID: " + materialId);
        }
          Tags tags = Tags.builder()
                .materialId(materialId)
                .type(Tags.MaterialType.valueOf(material.getType().name()))
                .build();
        
        // Add the provided tags
        if (tagList != null && !tagList.isEmpty()) {
            tags.setTagsFromStrings(tagList);
        }
        
        Tags savedTags = tagsRepository.save(tags);
        log.info("Created tags for material ID: {} with {} tags", materialId, savedTags.getTagCount());
        
        return savedTags;
    }
    public MaterialTagsResponse addTags(Long materialId, List<String> newTags){
    Tags tags = materialTagsRepository.findByMaterialId(materialId)
        .orElseThrow(()-> new ValidationException("Tags not founds for this material"));
        
        int initialCount = tags.getTagCount();
        
        if (newTags != null) {
            for (String tag : newTags) {
                tags.addTag(tag);
            }
        }
        
        Tags updatedTags = tagsRepository.save(tags);
        log.info("Added {} tags to material ID: {} (total: {})", 
                updatedTags.getTagCount() - initialCount, materialId, updatedTags.getTagCount());
        
        return updatedTags;
    }
    
    public MaterialTagsResponse removeTags(Long materialId, List<String> TagsToRemove){
        Tags tags = materialTagsRepository.findByMaterialId(materialId)
                .orElseThrow(()-> new ValidationException("There are no exisying tags"));
          
        int initialCount = tags.getTagCount();
        
        if (tagsToRemove != null) {
            for (String tag : tagsToRemove) {
                tags.removeTag(tag);
            }
        }
        
        Tags updatedTags = tagsRepository.save(tags);
        log.info("Removed {} tags from material ID: {} (remaining: {})", 
                initialCount - updatedTags.getTagCount(), materialId, updatedTags.getTagCount());
        
        return updatedTags;
    }
    
    @Transactional(readOnly = true)
    public List<Tags> findMaterialsByTag(Tags.MaterialType type, String tag) {
        return materialTagRepository.findBydTags(type, tag);
    }
    
    public MaterialTagsResponse getTagsByType(Tags.MaterialType type){
        return materialTagRepository.findByType(type);
    }
    
    public MaterialTagsResponse countTags(String tag){
        return materialTagRepository.countByTags(tag);
    
    }
    
    public MaterialTagsResponse updateTags(){
     Tags tags = MaterialTagsRepository.findByMaterialId(materialId)
                .orElseThrow(() -> new ValidationException("Tags not found for material ID: " + materialId));
        
        tags.clearTags();
        
        if (newTags != null) {
            tags.setTagsFromStrings(newTags);
        }
        
        Tags updatedTags = tagsRepository.save(tags);
        log.info("Replaced tags for material ID: {} with {} new tags", materialId, updatedTags.getTagCount());
        
        return updatedTags;
    }
    
}
