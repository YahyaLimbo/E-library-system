package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/materials")


public class MaterialController {
    private final MaterialService materialService;
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> getMaterial (@PathVariable Long id)
    {
    Optional<MaterialResponse> materialOptional = materialService.getMaterialById(id);
    
    return materialService.getMaterialById(id)
            .map(material->ResponseEntity.ok(material))
            .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<MaterialResponse> createMaterial(
            @Valid @RequestBody CreateMaterialRequest request
    ) {
        try {
        System.out.println("=== DEBUG: Received request ===");
        System.out.println("Title: " + request.getTitle());
        System.out.println("Author: " + request.getAuthor());
        System.out.println("Identifier: " + request.getIdentifier());
        
        MaterialResponse createdMaterial = materialService.createMaterial(request);
        
        System.out.println("=== DEBUG: Material created successfully ===");
        System.out.println("Created ID: " + createdMaterial.getMaterialid());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaterial);
        
    } catch (Exception e) {
        System.err.println("=== ERROR: Exception occurred ===");
        e.printStackTrace();
        return ResponseEntity.status(500).body(null);
    }

        //create material
        //MaterialResponse createdMaterial = materialService.createMaterial(request);
        //send request and expected 201
        //return ResponseEntity.status(HttpStatus.CREATED).body(createdMaterial);    
    }
    
   @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMaterialRequest request) {
     try {
        MaterialResponse updatedMaterial = materialService.updateMaterial(id, request);
        return ResponseEntity.ok(updatedMaterial);  
        
    } catch (ValidationException e) {
        return ResponseEntity.notFound().build(); 
    }
    }
    
    @DeleteMapping("/{id}") //remember it can be PUT,DELETE,POST,GET
    public ResponseEntity<MaterialResponse> deleteMaterial(
    @PathVariable Long id) //research properly notations
    {
        try{
        materialService.deleteMaterial(id); //use try statments to handle easily http requests
        return ResponseEntity.noContent().build();
        } catch(ValidationException e){
            return ResponseEntity.notFound().build();
        }
        
        
        
    }
 @GetMapping
    public ResponseEntity<Page<MaterialResponse>> getAllMaterials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MaterialResponse> materialPage = materialService.getAllMaterials(pageable);
        
        return ResponseEntity.ok(materialPage);
    }

    @GetMapping("/custom")
    public ResponseEntity<PageableResponse<MaterialResponse>> getMaterialsCustom(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
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
    @PostMapping("/search")
    public ResponseEntity<Page<MaterialSummary>> searchMaterials(
            @RequestBody MaterialSearchRequest searchRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "materialid") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Page<MaterialSummary> materialPage = materialService.searchMaterials(
            searchRequest, page, size, sortBy, sortDir);
        return ResponseEntity.ok(materialPage);
    }

    // Search by title
    @GetMapping("/search/title")
    public ResponseEntity<List<MaterialSummary>> searchByTitle(@RequestParam String title) {
        List<MaterialSummary> materials = materialService.searchByTitle(title);
        return ResponseEntity.ok(materials);
    }

    // Search by author
    @GetMapping("/search/author")
    public ResponseEntity<List<MaterialSummary>> searchByAuthor(@RequestParam String author) {
        List<MaterialSummary> materials = materialService.searchByAuthor(author);
        return ResponseEntity.ok(materials);
    }

    // Search by publisher
    @GetMapping("/search/publisher")
    public ResponseEntity<List<MaterialSummary>> searchByPublisher(@RequestParam String publisher) {
        List<MaterialSummary> materials = materialService.searchByPublisher(publisher);
        return ResponseEntity.ok(materials);
    }

    // Search in summary/description
    @GetMapping("/search/summary")
    public ResponseEntity<List<MaterialSummary>> searchInSummary(@RequestParam String keyword) {
        List<MaterialSummary> materials = materialService.searchInSummary(keyword);
        return ResponseEntity.ok(materials);
    }

    // Get materials by type
    @GetMapping("/type/{type}")
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
    public ResponseEntity<List<MaterialSummary>> getRecentMaterials() {
        List<MaterialSummary> materials = materialService.getRecentMaterials();
        return ResponseEntity.ok(materials);
    }

    // Get material statistics
    @GetMapping("/stats")
    public ResponseEntity<MaterialService.MaterialStats> getMaterialStats() {
        MaterialService.MaterialStats stats = materialService.getMaterialStats();
        return ResponseEntity.ok(stats);
    }

    // Get material by identifier
    @GetMapping("/identifier/{identifier}")
    public ResponseEntity<MaterialResponse> getMaterialByIdentifier(@PathVariable String identifier) {
        return materialService.getMaterialByIdentifier(identifier)
                .map(material -> ResponseEntity.ok(material))
                .orElse(ResponseEntity.notFound().build());
    }
}