package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MaterialTagService {
    
    private final MaterialRepository materialRepository;
    private final MaterialTagRepository materialTagRepository;
    
    public Tags createTags(Long materialId, List<String> tagList) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ValidationException("Material not found with ID: " + materialId));
        
        if (materialTagRepository.findByMaterialId(materialId).isPresent()) {
            throw new ValidationException("Tags already exist for material ID: " + materialId);
        }
        
        Tags tags = Tags.builder()
                .materialId(materialId)
                .type(material.getType().name())
                .build();
        
        if (tagList != null && !tagList.isEmpty()) {
            tags.setTagsFromStrings(tagList);
        }
        
        Tags savedTags = materialTagRepository.save(tags);
        log.info("Created tags for material ID: {} with {} tags", materialId, savedTags.getTagCount());
        
        return savedTags;
    }
    
    public Tags addTags(Long materialId, List<String> newTags) {
        Tags tags = materialTagRepository.findByMaterialId(materialId)
            .orElseThrow(() -> new ValidationException("Tags not found for this material"));
            
        int initialCount = tags.getTagCount();
        
        if (newTags != null) {
            for (String tag : newTags) {
                tags.addTag(tag);
            }
        }
        
        Tags updatedTags = materialTagRepository.save(tags);
        log.info("Added {} tags to material ID: {} (total: {})", 
                updatedTags.getTagCount() - initialCount, materialId, updatedTags.getTagCount());
        
        return updatedTags;
    }
    
    public Tags removeTags(Long materialId, List<String> tagsToRemove) {
        Tags tags = materialTagRepository.findByMaterialId(materialId)
                .orElseThrow(() -> new ValidationException("There are no existing tags"));
          
        int initialCount = tags.getTagCount();
        
        if (tagsToRemove != null) {
            for (String tag : tagsToRemove) {
                tags.removeTag(tag);
            }
        }
        
        Tags updatedTags = materialTagRepository.save(tags);
        log.info("Removed {} tags from material ID: {} (remaining: {})", 
                initialCount - updatedTags.getTagCount(), materialId, updatedTags.getTagCount());
        
        return updatedTags;
    }
    
    @Transactional(readOnly = true)
    public List<Tags> findMaterialsByTag(String tag) {
        List<Tags> allTags = materialTagRepository.findAll();
        List<Tags> results = allTags.stream()
                .filter(tags ->tags.getTags()!=null &&tags.getTags().contains(tag.toLowerCase()))
                .collect(Collectors.toList());
        return results;
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllUniqueTags() {
        List<Tags> allTags = materialTagRepository.findAll();
        return allTags.stream()
                .flatMap(tags -> tags.getTags().stream())
                .distinct()
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Long countTags(String tag) {
        // Temporary implementation using in-memory count
        List<Tags> allTags = materialTagRepository.findAll();
        return allTags.stream()
                .filter(tags -> tags.getTags().contains(tag.toLowerCase()))
                .count();
    }
    
    public Tags updateTags(Long materialId, List<String> newTags) {
        Tags tags = materialTagRepository.findByMaterialId(materialId)
                .orElseThrow(() -> new ValidationException("Tags not found for material ID: " + materialId));
        
        tags.clearTags();
        
        if (newTags != null) {
            tags.setTagsFromStrings(newTags);
        }
        
        Tags updatedTags = materialTagRepository.save(tags);
        log.info("Replaced tags for material ID: {} with {} new tags", materialId, updatedTags.getTagCount());
        
        return updatedTags;
    }
}