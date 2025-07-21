package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

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
        try {
            log.info("Searching for materials with tag: {}", tag);
            
            // First try the database query method
            try {
                List<Tags> results = materialTagRepository.findByTags(tag.trim().toLowerCase());
                log.info("Database search found {} materials with tag: {}", results.size(), tag);
                return results;
            } catch (Exception dbException) {
                log.warn("Database search failed, falling back to in-memory search: {}", dbException.getMessage());
                
                // Fallback to in-memory search
                List<Tags> allTags = materialTagRepository.findAll();
                List<Tags> results = new ArrayList<>();
                
                String searchTag = tag.trim().toLowerCase();
                for (Tags tags : allTags) {
                    if (tags.getTags() != null) {
                        for (String existingTag : tags.getTags()) {
                            if (existingTag.toLowerCase().equals(searchTag)) {
                                results.add(tags);
                                break; // Don't add the same Tags record multiple times
                            }
                        }
                    }
                }
                
                log.info("In-memory search found {} materials with tag: {}", results.size(), tag);
                return results;
            }
        } catch (Exception e) {
            log.error("Error searching for tag '{}': {}", tag, e.getMessage(), e);
            throw new RuntimeException("Error searching for materials with tag: " + tag, e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllUniqueTags() {
        try {
            List<Tags> allTags = materialTagRepository.findAll();
            List<String> uniqueTags = new ArrayList<>();
            
            for (Tags tags : allTags) {
                if (tags.getTags() != null) {
                    for (String tag : tags.getTags()) {
                        if (!uniqueTags.contains(tag)) {
                            uniqueTags.add(tag);
                        }
                    }
                }
            }
            
            // Sort for better user experience
            uniqueTags.sort(String.CASE_INSENSITIVE_ORDER);
            return uniqueTags;
            
        } catch (Exception e) {
            log.error("Error getting all unique tags: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving unique tags", e);
        }
    }
    
    @Transactional(readOnly = true)
    public Long countTags(String tag) {
        try {
            // Try database count first
            try {
                return materialTagRepository.countByTags(tag.trim().toLowerCase());
            } catch (Exception dbException) {
                log.warn("Database count failed, falling back to in-memory count: {}", dbException.getMessage());
                
                // Fallback to in-memory count
                List<Tags> allTags = materialTagRepository.findAll();
                long count = 0;
                
                String searchTag = tag.trim().toLowerCase();
                for (Tags tags : allTags) {
                    if (tags.getTags() != null) {
                        for (String existingTag : tags.getTags()) {
                            if (existingTag.toLowerCase().equals(searchTag)) {
                                count++;
                                break; // Don't count the same Tags record multiple times
                            }
                        }
                    }
                }
                
                return count;
            }
        } catch (Exception e) {
            log.error("Error counting tag '{}': {}", tag, e.getMessage(), e);
            return 0L;
        }
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