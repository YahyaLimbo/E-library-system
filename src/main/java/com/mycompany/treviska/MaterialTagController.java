package com.mycompany.treviska;
import com.mycompany.treviska.security.Permissions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    
    public static class TagResponse{
        public List<String> tags;
        public TagRequest(List<String> tags) {
            this.tags = tags;
    }
}
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Tags> createTags(@PathVariable Long materialId, @Valid @RequestBody TagRequest request, Authentication authentication){
        try {
            Tags tags = MaterialTagService.createTags(materialId, request.tags);
            return ResponseEntity.ok(tags);
        }catch(validationException e){
            return ResponseEntity.badRequest.build();
        
        }
}
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Tags> addTags(@PathVariable Long materialId, @Valid @RequestBody TagRequest request,
            Authentication authentication){
        try{
     Tags tags = materialTagService.addTags(materialId, request.tags);
            return ResponseEntity.ok(tags);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
        
    }
    @PostMapping("/remove")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Tags> removeTags(@PathVariable Long materialId, @Valid @RequestBody TagRequest request,
            Authentication authentication){
        try{
        Tags tags = MaterialTagService.removeTags(materialId, request.tags);
        return ResponseEntity.ok(tags);
        }catch (ValidationException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/search")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<Tags> findMaterialByTags(@PathVariable String tag ){
        try{
            List<Tags>materials = MaterialTagService.findMaterialByTags(tag);
            return ResponseEntity.ok(materials);
        }catch(Exception e){
             return ResponseEntity.badRequest().build();
        }   
    }
    @PostMapping("/unique")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    //getTypebytags
    public ResponseEntity<Tags> getTagsByType(@PathVariable String tag ){
        try {
            List<String> uniqueTags = tagsService.getAllUniqueTags();
            return ResponseEntity.ok(uniqueTags);
        } catch (Exception e) {
            log.warn("Failed to get unique tags: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/count")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<Tags> countTags(@PathVariable String tag){
        try{
        Long count = MaterialTagService.countTags(tag);
        return ResponseEntity.ok(count);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
     public ResponseEntity<Tags> updateTags(@PathVariable Long materialId,
            @Valid @RequestBody TagRequest request,
            Authentication authentication){
         try{
         Tags tags = materialTagRepository.updateTags(materialId, request.tags);
         return ResponseEntity.ok(tags);
         }
         catch (ValidationException e){
         return ResponseEntity.badRequest().build();
         }
    }
    
    
}
