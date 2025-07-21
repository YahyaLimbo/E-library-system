package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api") // Add explicit base mapping
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*") // Add CORS support
public class MaterialTagController {
    
    private final MaterialTagService materialTagService;
    
    public static class TagRequest {
        public List<String> tags;
        
        public TagRequest() {}
        
        public TagRequest(List<String> tags) {
            this.tags = tags;
        }
    }
    
    public static class TagResponse {
        public List<String> tags;
        
        public TagResponse() {}
        
        public TagResponse(List<String> tags) {
            this.tags = tags;
        }
    }
    
    // === MATERIAL TAG MANAGEMENT ===
    
    @PostMapping("/materials/{materialId}/tags")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> createTags(@PathVariable Long materialId, 
            @Valid @RequestBody TagRequest request, 
            Authentication authentication) {
        try {
            Tags tags = materialTagService.createTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            log.warn("Validation error creating tags: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error creating tags: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/materials/{materialId}/tags/add")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> addTags(@PathVariable Long materialId, 
            @Valid @RequestBody TagRequest request,
            Authentication authentication) {
        try {
            Tags tags = materialTagService.addTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            log.warn("Validation error adding tags: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error adding tags: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/materials/{materialId}/tags/remove")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> removeTags(@PathVariable Long materialId, 
            @Valid @RequestBody TagRequest request,
            Authentication authentication) {
        try {
            Tags tags = materialTagService.removeTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            log.warn("Validation error removing tags: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error removing tags: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    @PutMapping("/materials/{materialId}/tags")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> updateTags(@PathVariable Long materialId,
            @Valid @RequestBody TagRequest request,
            Authentication authentication) {
        try {
            Tags tags = materialTagService.updateTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            log.warn("Validation error updating tags: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating tags: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    // === TAG SEARCH AND DISCOVERY ===
    
    @GetMapping("/tags/unique")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<List<String>> getAllUniqueTags() {
        try {
            log.info("Request for all unique tags");
            List<String> uniqueTags = materialTagService.getAllUniqueTags();
            log.info("Returning {} unique tags", uniqueTags.size());
            return ResponseEntity.ok(uniqueTags);
        } catch (Exception e) {
            log.error("Failed to get unique tags: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping("/tags/search/{tag}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<List<TagsResponseDto>> findMaterialsByTag(@PathVariable String tag) {
        try {
            log.info("=== TAG SEARCH REQUEST ===");
            log.info("Searching for materials with tag: '{}'", tag);
            
            // Validate input
            if (tag == null || tag.trim().isEmpty()) {
                log.warn("Empty or null tag provided for search");
                return ResponseEntity.badRequest().build();
            }
            
            // Clean the tag (remove quotes if present)
            String cleanTag = cleanTagString(tag);
            log.info("Cleaned tag for search: '{}'", cleanTag);
            
            List<Tags> materials = materialTagService.findMaterialsByTag(cleanTag);
            log.info("Found {} materials with tag: '{}'", materials.size(), cleanTag);
            
            // Convert to DTOs to avoid Hibernate lazy loading serialization issues
            List<TagsResponseDto> responseDtos = materials.stream()
                    .map(TagsResponseDto::fromEntity)
                    .collect(java.util.stream.Collectors.toList());
            
            log.info("=== TAG SEARCH COMPLETED ===");
            
            return ResponseEntity.ok(responseDtos);
            
        } catch (Exception e) {
            log.error("=== TAG SEARCH ERROR ===");
            log.error("Error searching for materials with tag '{}': {}", tag, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }   
    }
    
    @GetMapping("/tags/count/{tag}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<Long> countTagUsage(@PathVariable String tag) {
        try {
            log.info("Counting usage of tag: '{}'", tag);
            
            if (tag == null || tag.trim().isEmpty()) {
                log.warn("Empty tag provided for counting");
                return ResponseEntity.badRequest().build();
            }
            
            String cleanTag = cleanTagString(tag);
            Long count = materialTagService.countTags(cleanTag);
            
            log.info("Tag '{}' is used {} times", cleanTag, count);
            return ResponseEntity.ok(count);
            
        } catch (Exception e) {
            log.error("Error counting tag '{}': {}", tag, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    // === DEBUG ENDPOINTS ===
    
    @GetMapping("/tags/debug/test")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<String> debugTest() {
        log.info("Debug test endpoint called");
        return ResponseEntity.ok("Tag controller is working! Timestamp: " + System.currentTimeMillis());
    }
    
    @GetMapping("/tags/debug/list-first-few")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<List<String>> debugListFirstFewTags() {
        try {
            List<String> allTags = materialTagService.getAllUniqueTags();
            List<String> firstFew = allTags.size() > 5 ? allTags.subList(0, 5) : allTags;
            log.info("Debug: Returning first {} tags out of {} total", firstFew.size(), allTags.size());
            return ResponseEntity.ok(firstFew);
        } catch (Exception e) {
            log.error("Debug endpoint error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    // === HELPER METHODS ===
    
    /**
     * Cleans tag strings by removing quotes and trimming whitespace
     */
    private String cleanTagString(String tag) {
        if (tag == null) {
            return "";
        }
        
        String cleanTag = tag.trim();
        
        // Remove single quotes if they wrap the entire string
        if (cleanTag.startsWith("'") && cleanTag.endsWith("'") && cleanTag.length() > 1) {
            cleanTag = cleanTag.substring(1, cleanTag.length() - 1);
        }
        
        // Remove double quotes if they wrap the entire string  
        if (cleanTag.startsWith("\"") && cleanTag.endsWith("\"") && cleanTag.length() > 1) {
            cleanTag = cleanTag.substring(1, cleanTag.length() - 1);
        }
        
        return cleanTag.trim();
    }
}