package com.mycompany.treviska;

import  lombok.RequiredArgsConstructor;
import  org.springframework.http.ResponseEntity;
import  org.springframework.web.bind.annotation.*;
import  java.util.Optional;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

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
    
}