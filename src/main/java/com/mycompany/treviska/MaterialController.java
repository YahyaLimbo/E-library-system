package com.mycompany.treviska;

import java.util.ArrayList;
import com.mycompany.treviska.security.Permissions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;  
import org.springframework.core.io.Resource;              
import org.springframework.http.HttpHeaders;             
import org.springframework.http.MediaType; 

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/materials")
@Slf4j  // ADD THIS - provides logging with 'log' variable
@CrossOrigin(origins = "*")  // ADD THIS - for CORS if needed
public class MaterialController {
    private final MaterialService materialService;
    private final MaterialFileService materialFileService;
    private MaterialSummary convertToSummary(MaterialResponse response) {
    return MaterialSummary.builder()
            .materialid(response.getMaterialid())
            .title(response.getTitle())
            .author(response.getAuthor())
            .identifier(response.getIdentifier())
            .type(response.getType())
            .summary(response.getSummary())
            .datePublished(response.getDatePublished())
            .publisher(response.getPublisher())
            .contributors(response.getContributors())
            .format(response.getFormat())
            .language(response.getLanguage())
            .createdAt(response.getCreatedAt())
            .updatedAt(response.getUpdatedAt())
            .build();
}

    // ========== ADD PUBLIC ENDPOINTS (duplicate some existing ones with /public prefix) ==========
    
    /**
     * PUBLIC: Get material by ID (no authentication required)
     */
    @GetMapping("/public/{id}")
    public ResponseEntity<MaterialResponse> getMaterialPublic(@PathVariable Long id) {
        log.info("Public request to get material with ID: {}", id);
        Optional<MaterialResponse> materialOptional = materialService.getMaterialById(id);
        
        return materialService.getMaterialById(id)
                .map(material -> ResponseEntity.ok(material))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUBLIC: Get all materials (limited info, no authentication required)
     */
    @GetMapping("/public")
    public ResponseEntity<Page<MaterialSummary>> getAllMaterialsPublic(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("Public request to get all materials");
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MaterialResponse> materialPageResponse = materialService.getAllMaterials(pageable);
        Page<MaterialSummary> materialPage = materialPageResponse.map(this::convertToSummary);
        
        return ResponseEntity.ok(materialPage);
    }

    /**
     * PUBLIC: Search materials (basic search, no authentication required)
     */
    @PostMapping("/public/search")
    public ResponseEntity<Page<MaterialSummary>> searchMaterialsPublic(
            @RequestBody MaterialSearchRequest searchRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("Public search request for materials");
        Page<MaterialSummary> materialPage = materialService.searchMaterials(
            searchRequest, page, size, sortBy, sortDir);
        return ResponseEntity.ok(materialPage);
    }

    // ========== MODIFY EXISTING ENDPOINTS TO ADD SECURITY ==========

    // MODIFY THIS - Add authentication requirement
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")  // ADD THIS
    public ResponseEntity<MaterialResponse> getMaterial(
            @PathVariable Long id,
            Authentication authentication) {  // ADD Authentication parameter
        
        log.info("Authenticated user '{}' requesting material with ID: {}", authentication.getName(), id);  // ADD logging
        
        Optional<MaterialResponse> materialOptional = materialService.getMaterialById(id);
        
        return materialService.getMaterialById(id)
                .map(material->ResponseEntity.ok(material))
                .orElse(ResponseEntity.notFound().build());
    }

    // MODIFY THIS - Add security for creating materials
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")  // ADD THIS
    public ResponseEntity<MaterialResponse> createMaterial(
            @Valid @RequestBody CreateMaterialRequest request,
            Authentication authentication) {  // ADD Authentication parameter
        
        // ADD PERMISSION CHECK
        if (!Permissions.hasPermission(authentication, "CREATE_MATERIAL")) {
            log.warn("User '{}' attempted to create material without permission", authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            log.info("User '{}' creating new material: {}", authentication.getName(), request.getTitle());  // REPLACE System.out with log
            log.debug("Title: {}", request.getTitle());  // REPLACE System.out with log
            log.debug("Author: {}", request.getAuthor());
            log.debug("Identifier: {}", request.getIdentifier());
            
            MaterialResponse createdMaterial = materialService.createMaterial(request);
            
            log.info("Material created successfully with ID: {}", createdMaterial.getMaterialid());  // REPLACE System.out with log
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMaterial);
            
        } catch (Exception e) {
            log.error("Error creating material: {}", e.getMessage(), e);  // REPLACE System.err with log
            return ResponseEntity.status(500).body(null);
        }
    }
    
    // MODIFY THIS - Add security for updating materials
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")  // ADD THIS
    public ResponseEntity<MaterialResponse> updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMaterialRequest request,
            Authentication authentication) {  // ADD Authentication parameter
        
        // ADD PERMISSION CHECK
        if (!Permissions.hasPermission(authentication, "UPDATE_MATERIAL")) {
            log.warn("User '{}' attempted to update material without permission", authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            log.info("User '{}' updating material with ID: {}", authentication.getName(), id);  // ADD logging
            MaterialResponse updatedMaterial = materialService.updateMaterial(id, request);
            return ResponseEntity.ok(updatedMaterial);  
            
        } catch (ValidationException e) {
            log.warn("Validation error updating material {}: {}", id, e.getMessage());  // ADD logging
            return ResponseEntity.notFound().build(); 
        }
    }
    
    // MODIFY THIS - Add security for deleting materials
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // ADD THIS - Only admins can delete
    public ResponseEntity<MaterialResponse> deleteMaterial(
            @PathVariable Long id,
            Authentication authentication) {  // ADD Authentication parameter
        
        // ADD PERMISSION CHECK
        if (!Permissions.hasPermission(authentication, "DELETE_MATERIAL")) {
            log.warn("User '{}' attempted to delete material without permission", authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try{
            log.info("Admin user '{}' deleting material with ID: {}", authentication.getName(), id);  // ADD logging
            materialService.deleteMaterial(id);
            return ResponseEntity.noContent().build();
        } catch(ValidationException e){
            log.warn("Error deleting material {}: {}", id, e.getMessage());  // ADD logging
            return ResponseEntity.notFound().build();
        }
    }

    // MODIFY THIS - Add authentication requirement
    @GetMapping
    @PreAuthorize("hasRole('USER')")  // ADD THIS
    public ResponseEntity<Page<MaterialResponse>> getAllMaterials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Authentication authentication) {  // ADD Authentication parameter
        
        log.info("Authenticated user '{}' requesting all materials", authentication.getName());  // ADD logging
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MaterialResponse> materialPage = materialService.getAllMaterials(pageable);
        
        return ResponseEntity.ok(materialPage);
    }

    // MODIFY THIS - Add authentication requirement  
    @GetMapping("/custom")
    @PreAuthorize("hasRole('USER')")  // ADD THIS
    public ResponseEntity<PageableResponse<MaterialResponse>> getMaterialsCustom(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Authentication authentication) {  // ADD Authentication parameter
        
        log.info("Authenticated user '{}' requesting custom materials view", authentication.getName());  // ADD logging
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MaterialResponse> materialPage = materialService.getAllMaterials(pageable);
        
        List<MaterialResponse> materialDTOs = materialPage.getContent();
        
        PageableResponse<MaterialResponse> response = new PageableResponse<>(
            materialDTOs,
            materialPage.getNumber(),
            materialPage.getSize(),
            materialPage.getTotalElements(),
            materialPage.getTotalPages(),
            materialPage.isFirst(),
            materialPage.isLast()
        );
        
        return ResponseEntity.ok(response);
    }

    // MODIFY THIS - Add authentication for advanced search
    @PostMapping("/search")
    @PreAuthorize("hasRole('USER')")  // ADD THIS
    public ResponseEntity<Page<MaterialSummary>> searchMaterials(
            @RequestBody MaterialSearchRequest searchRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Authentication authentication) {  // ADD Authentication parameter
        
        log.info("Authenticated user '{}' performing advanced search", authentication.getName());  // ADD logging
        
        Page<MaterialSummary> materialPage = materialService.searchMaterials(
            searchRequest, page, size, sortBy, sortDir);
        return ResponseEntity.ok(materialPage);
    }

    // Keep these search endpoints as they are, but consider adding @PreAuthorize("hasRole('USER')") if you want them secured

    // Search by title
    @GetMapping("/search/title")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<List<MaterialSummary>> searchByTitle(@RequestParam String title) {
        List<MaterialSummary> materials = materialService.searchByTitle(title);
        return ResponseEntity.ok(materials);
    }

    // Search by author  
    @GetMapping("/search/author")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<List<MaterialSummary>> searchByAuthor(@RequestParam String author) {
        List<MaterialSummary> materials = materialService.searchByAuthor(author);
        return ResponseEntity.ok(materials);
    }

    // Search by publisher
    @GetMapping("/search/publisher")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<List<MaterialSummary>> searchByPublisher(@RequestParam String publisher) {
        List<MaterialSummary> materials = materialService.searchByPublisher(publisher);
        return ResponseEntity.ok(materials);
    }

    // Search in summary/description
    @GetMapping("/search/summary")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<List<MaterialSummary>> searchInSummary(@RequestParam String keyword) {
        List<MaterialSummary> materials = materialService.searchInSummary(keyword);
        return ResponseEntity.ok(materials);
    }

    // Get materials by type
    @GetMapping("/type/{type}")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<List<MaterialSummary>> getMaterialsByType(@PathVariable String type) {
        try {
            Material.MaterialType materialType = Material.MaterialType.valueOf(type.toUpperCase());
            List<MaterialSummary> materials = materialService.getMaterialsByType(materialType);
            return ResponseEntity.ok(materials);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get materials by format
    @GetMapping("/format/{format}")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<List<MaterialSummary>> getMaterialsByFormat(@PathVariable String format) {
        try {
            Material.MaterialFormat materialFormat = Material.MaterialFormat.valueOf(format.toUpperCase());
            List<MaterialSummary> materials = materialService.getMaterialsByFormat(materialFormat);
            return ResponseEntity.ok(materials);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get recent materials (last 30 days)
    @GetMapping("/recent")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<List<MaterialSummary>> getRecentMaterials() {
        List<MaterialSummary> materials = materialService.getRecentMaterials();
        return ResponseEntity.ok(materials);
    }

    // MODIFY THIS - Add security for viewing detailed stats
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")  // ADD THIS
    public ResponseEntity<MaterialService.MaterialStats> getMaterialStats(
            Authentication authentication) {  // ADD Authentication parameter
        
        // ADD PERMISSION CHECK
        if (!Permissions.hasPermission(authentication, "VIEW_STATS")) {
            log.warn("User '{}' attempted to view stats without permission", authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        log.info("User '{}' requesting material statistics", authentication.getName());  // ADD logging
        MaterialService.MaterialStats stats = materialService.getMaterialStats();
        return ResponseEntity.ok(stats);
    }

    // ADD PUBLIC VERSION OF STATS
    @GetMapping("/public/stats")
    public ResponseEntity<MaterialService.MaterialStats> getMaterialStatsPublic() {
        log.info("Public request for basic material statistics");
        MaterialService.MaterialStats stats = materialService.getMaterialStats();  // CREATE THIS METHOD
        return ResponseEntity.ok(stats);
    }

    // Get material by identifier
    @GetMapping("/identifier/{identifier}")
    @PreAuthorize("hasRole('USER')")  // ADD THIS
    public ResponseEntity<MaterialResponse> getMaterialByIdentifier(
            @PathVariable String identifier,
            Authentication authentication) {  // ADD Authentication parameter
        
        log.info("Authenticated user '{}' requesting material by identifier: {}", authentication.getName(), identifier);  // ADD logging
        
        return materialService.getMaterialByIdentifier(identifier)
                .map(material -> ResponseEntity.ok(material))
                .orElse(ResponseEntity.notFound().build());
    }

    // ========== ADD NEW SECURE ENDPOINTS ==========

    /**
     * Get materials created by current user
     */
    @GetMapping("/my-materials")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<MaterialResponse>> getMyMaterials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            Authentication authentication) {
        
        log.info("User '{}' requesting their personal materials", authentication.getName());
        
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // CREATE THIS METHOD in MaterialService
        Page<MaterialResponse> materials = materialService.getAllMaterials(pageable);
        return ResponseEntity.ok(materials);
    }

    /**
     * Bulk create materials (Admin only)
     */
    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MaterialResponse>> createMaterialsBulk(
            @Valid @RequestBody List<CreateMaterialRequest> requests,
            Authentication authentication) {
        
        if (!Permissions.hasPermission(authentication, "BULK_CREATE_MATERIAL")) {
            log.warn("User '{}' attempted bulk create without permission", authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        log.info("Admin user '{}' creating {} materials in bulk", authentication.getName(), requests.size());
 
        List<MaterialResponse> materials = new ArrayList<>();
        for (CreateMaterialRequest request : requests) {
        try {
        materials.add(materialService.createMaterial(request));
    } catch (Exception e) {
        log.error("Error creating material in bulk: {}", e.getMessage());
        // Continue with next material
    }
}
        return ResponseEntity.status(HttpStatus.CREATED).body(materials);
    }

    // === MODIFY FILE MANAGEMENT ENDPOINTS TO ADD SECURITY ===

    // MODIFY THIS - Add security for file upload
    @PostMapping("/{id}/files")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")  // ADD THIS
    public ResponseEntity<MaterialFileResponse> uploadFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) Boolean isPrimary,
            Authentication authentication) {  // ADD Authentication parameter
        
        // ADD PERMISSION CHECK
        if (!Permissions.hasPermission(authentication, "UPLOAD_FILES")) {
            log.warn("User '{}' attempted to upload file without permission", authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            log.info("User '{}' uploading file for material ID: {}", authentication.getName(), id);  // ADD logging
            
            FileUploadRequest request = FileUploadRequest.builder()
                    .isPrimary(isPrimary != null ? isPrimary : false)
                    .build();
            
            MaterialFileResponse uploadedFile = materialFileService.uploadFile(id, file, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFile);
            
        } catch (ValidationException e) {
            log.warn("Validation error uploading file: {}", e.getMessage());  // ADD logging
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage(), e);  // REPLACE e.printStackTrace() with log
            return ResponseEntity.status(500).build();
        }
    }

    // MODIFY THIS - Add authentication for viewing files
    @GetMapping("/{id}/files")
    @PreAuthorize("hasRole('USER')")  // ADD THIS
    public ResponseEntity<List<MaterialFileResponse>> getFilesByMaterial(
            @PathVariable Long id,
            Authentication authentication) {  // ADD Authentication parameter
        
        log.info("User '{}' requesting files for material ID: {}", authentication.getName(), id);  // ADD logging
        
        List<MaterialFileResponse> files = materialFileService.getFilesByMaterialId(id);
        return ResponseEntity.ok(files);
    }

    // Keep download endpoints as they are, but consider adding authentication if needed
    @GetMapping("/files/{fileId}/download")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        try {
            FileDownloadResponse fileResponse = materialFileService.getFileForDownload(fileId);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileResponse.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                           "attachment; filename=\"" + fileResponse.getFileName() + "\"")
                    .body(fileResponse.getResource());
                    
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error downloading file: {}", e.getMessage(), e);  // REPLACE e.printStackTrace() with log
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/files/{fileId}/preview")
    // @PreAuthorize("hasRole('USER')")  // OPTIONALLY ADD THIS
    public ResponseEntity<Resource> previewFile(@PathVariable Long fileId) {
        try {
            FileDownloadResponse fileResponse = materialFileService.getFileForDownload(fileId);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileResponse.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(fileResponse.getResource());
                    
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error previewing file: {}", e.getMessage(), e);  // REPLACE e.printStackTrace() with log
            return ResponseEntity.status(500).build();
        }
    }

    // ========== ADD EXCEPTION HANDLERS ==========

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        log.error("Illegal argument error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("Runtime error in MaterialController: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing your request");
    }
}