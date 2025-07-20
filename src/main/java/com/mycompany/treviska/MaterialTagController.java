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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/materials/{materialId}/tags")
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
    
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> createTags(@PathVariable Long materialId, 
            @Valid @RequestBody TagRequest request, 
            Authentication authentication) {
        try {
            Tags tags = materialTagService.createTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> addTags(@PathVariable Long materialId, 
            @Valid @RequestBody TagRequest request,
            Authentication authentication) {
        try {
            Tags tags = materialTagService.addTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/remove")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> removeTags(@PathVariable Long materialId, 
            @Valid @RequestBody TagRequest request,
            Authentication authentication) {
        try {
            Tags tags = materialTagService.removeTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/search/{tag}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<List<Tags>> findMaterialByTags(@PathVariable String tag) {
        try {
            List<Tags> materials = materialTagService.findMaterialsByTag(tag);
            return ResponseEntity.ok(materials);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }   
    }
    
    @GetMapping("/unique")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<List<String>> getAllUniqueTags() {
        try {
            List<String> uniqueTags = materialTagService.getAllUniqueTags();
            return ResponseEntity.ok(uniqueTags);
        } catch (Exception e) {
            log.warn("Failed to get unique tags: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/count/{tag}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<Long> countTags(@PathVariable String tag) {
        try {
            Long count = materialTagService.countTags(tag);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<TagResponse> updateTags(@PathVariable Long materialId,
            @Valid @RequestBody TagRequest request,
            Authentication authentication) {
        try {
            Tags tags = materialTagService.updateTags(materialId, request.tags);
            return ResponseEntity.ok(new TagResponse(tags.getTags()));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}