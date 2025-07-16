
package com.mycompany.treviska;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MaterialService {
    private final MaterialRepository materialRepository;
    
    // Create new material
    public MaterialResponse createMaterial(CreateMaterialRequest request) {
        log.info("Creating new material: {}", request.getTitle());
        
        // Check if identifier already exists
        if (materialRepository.existsByIdentifier(request.getIdentifier())) {
            throw new ValidationException("Material with identifier " + request.getIdentifier() + " already exists");
        }
        
        Material material = new Material();
        material.setTitle(request.getTitle());
        material.setAuthor(request.getAuthor());
        material.setIdentifier(request.getIdentifier());
        material.setType(request.getType());
        material.setSummary(request.getSummary());
        material.setDatePublished(request.getDatePublished());
        material.setPublisher(request.getPublisher());
        material.setContributors(request.getContributors());
        material.setFormat(request.getFormat());
        material.setLanguage(request.getLanguage());
        
        Material savedMaterial = materialRepository.save(material);
        log.info("Material created successfully with ID: {}", savedMaterial.getMaterialid());
        
        return new MaterialResponse(savedMaterial);
    }
    
    // Get material by ID
    @Transactional(readOnly = true)
    public Optional<MaterialResponse> getMaterialById(Long id) {
        return materialRepository.findById(id)
                .map(MaterialResponse::new);
    }
    
    // Get material by identifier
    @Transactional(readOnly = true)
    public Optional<MaterialResponse> getMaterialByIdentifier(String identifier) {
        return materialRepository.findByIdentifier(identifier)
                .map(MaterialResponse::new);
    }
    
    // Get all materials with pagination
    @Transactional(readOnly = true)
    public Page<MaterialSummary> getAllMaterials(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return materialRepository.findAll(pageable)
                .map(MaterialSummary::new);
    }
    
    // Search materials
    @Transactional(readOnly = true)
    public Page<MaterialSummary> searchMaterials(MaterialSearchRequest searchRequest, 
            int page, int size, String sortBy, String sortDirection) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return materialRepository.searchMaterials(
                searchRequest.getTitle(), 
                searchRequest.getAuthor(), 
                searchRequest.getPublisher(),
                searchRequest.getType(), 
                searchRequest.getFormat(), 
                searchRequest.getLanguage(),
                pageable)
                .map(MaterialSummary::new);
    }
    
    // Update material
    public MaterialResponse updateMaterial(Long id, UpdateMaterialRequest request) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Material not found with ID: " + id));
        
        log.info("Updating material with ID: {}", id);
        
        // Update only non-null fields
        if (request.getTitle() != null) {
            material.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            material.setAuthor(request.getAuthor());
        }
        if (request.getType() != null) {
            material.setType(request.getType());
        }
        if (request.getSummary() != null) {
            material.setSummary(request.getSummary());
        }
        if (request.getDatePublished() != null) {
            material.setDatePublished(request.getDatePublished());
        }
        if (request.getPublisher() != null) {
            material.setPublisher(request.getPublisher());
        }
        if (request.getContributors() != null) {
            material.setContributors(request.getContributors());
        }
        if (request.getFormat() != null) {
            material.setFormat(request.getFormat());
        }
        if (request.getLanguage() != null) {
            material.setLanguage(request.getLanguage());
        }
        
        Material updatedMaterial = materialRepository.save(material);
        log.info("Material updated successfully: {}", updatedMaterial.getMaterialid());
        
        return new MaterialResponse(updatedMaterial);
    }
    
    // Delete material
    public void deleteMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new ValidationException("Material not found with ID: " + id);
        }
        
        log.info("Deleting material with ID: {}", id);
        materialRepository.deleteById(id);
        log.info("Material deleted successfully: {}", id);
    }
    
    // Get materials by type
    @Transactional(readOnly = true)
    public List<MaterialSummary> getMaterialsByType(Material.MaterialType type) {
        return materialRepository.findByType(type)
                .stream()
                .map(MaterialSummary::new)
                .collect(Collectors.toList());
    }
    
    // Get materials by format
    @Transactional(readOnly = true)
    public List<MaterialSummary> getMaterialsByFormat(Material.MaterialFormat format) {
        return materialRepository.findByFormat(format)
                .stream()
                .map(MaterialSummary::new)
                .collect(Collectors.toList());
    }
    
    // Search by title
    @Transactional(readOnly = true)
    public List<MaterialSummary> searchByTitle(String title) {
        return materialRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(MaterialSummary::new)
                .collect(Collectors.toList());
    }
    
    // Search by author
    @Transactional(readOnly = true)
    public List<MaterialSummary> searchByAuthor(String author) {
        return materialRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(MaterialSummary::new)
                .collect(Collectors.toList());
    }
    
    // Search by publisher
    @Transactional(readOnly = true)
    public List<MaterialSummary> searchByPublisher(String publisher) {
        return materialRepository.findByPublisherContainingIgnoreCase(publisher)
                .stream()
                .map(MaterialSummary::new)
                .collect(Collectors.toList());
    }
    
    // Search in summary/description
    @Transactional(readOnly = true)
    public List<MaterialSummary> searchInSummary(String keyword) {
        return materialRepository.findBySummaryContainingIgnoreCase(keyword)
                .stream()
                .map(MaterialSummary::new)
                .collect(Collectors.toList());
    }
    
    // Get recent materials (last 30 days)
    @Transactional(readOnly = true)
    public List<MaterialSummary> getRecentMaterials() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return materialRepository.findRecentMaterials(thirtyDaysAgo)
                .stream()
                .map(MaterialSummary::new)
                .collect(Collectors.toList());
    }
    
    // Get material statistics
    @Transactional(readOnly = true)
    public MaterialStats getMaterialStats() {
        List<Object[]> typeStats = materialRepository.countMaterialsByType();
        List<Object[]> formatStats = materialRepository.countMaterialsByFormat();
        long totalMaterials = materialRepository.count();
        
        return new MaterialStats(totalMaterials, typeStats, formatStats);
    }
    
    // Helper class for statistics
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class MaterialStats {
        private long totalMaterials;
        private List<Object[]> materialsByType;
        private List<Object[]> materialsByFormat;
    }
    public Page<MaterialResponse> getAllMaterials(Pageable pageable) {
    Page<Material> materialPage = materialRepository.findAll(pageable);
    return materialPage.map(this::convertToResponse);
}

private MaterialResponse convertToResponse(Material material) {
    return MaterialResponse.builder()
        .materialid(material.getMaterialid())  // Make sure this matches your Material entity getter
        .title(material.getTitle())
        .author(material.getAuthor())
        .identifier(material.getIdentifier())
        .build();
}
}
