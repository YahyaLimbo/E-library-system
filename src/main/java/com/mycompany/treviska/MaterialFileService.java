package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MaterialFileService {
    
    private final MaterialFileRepository materialFileRepository;
    private final MaterialRepository materialRepository;
    
    public MaterialFileResponse uploadFile(Long materialId, MultipartFile file, FileUploadRequest request) throws IOException {
        // Verify material exists
        if (!materialRepository.existsById(materialId)) {
            throw new ValidationException("Material not found with ID: " + materialId);
        }
        
        // Validate file
        if (file.isEmpty()) {
            throw new ValidationException("File is empty");
        }
        
        // Check file size (e.g., max 50MB)
        if (file.getSize() > 50 * 1024 * 1024) {
            throw new ValidationException("File size too large. Maximum 50MB allowed.");
        }
        
        // If this is set as primary, unset other primary files
        if (request != null && Boolean.TRUE.equals(request.getIsPrimary())) {
            unsetPrimaryFile(materialId);
        }
         byte[] fileBytes = file.getBytes();
        if (fileBytes == null || fileBytes.length == 0) {
        throw new ValidationException("File content cannot be empty");
        }
        // Create file record with content
        MaterialFile materialFile = MaterialFile.builder()
                .materialId(materialId)
                .name(file.getOriginalFilename())
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .fileContent(fileBytes) // Store file content in database
                .isPrimary(request != null ? request.getIsPrimary() : false)
                .downloadCount(0)
                .build();
        System.out.println("About to save - fileContent length: " + 
                      (materialFile.getFileContent() != null ? materialFile.getFileContent().length : "NULL"));
    
        MaterialFile savedFile = materialFileRepository.save(materialFile);
        return new MaterialFileResponse(savedFile);
    }
    
    @Transactional(readOnly = true)
    public List<MaterialFileResponse> getFilesByMaterialId(Long materialId) {
        List<MaterialFile> files = materialFileRepository.findByMaterialIdWithoutContent(materialId);
        return files.stream()
                .map(MaterialFileResponse::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<MaterialFileResponse> getPrimaryFile(Long materialId) {
        return materialFileRepository.findPrimaryByMaterialIdWithoutContent(materialId)
                .map(MaterialFileResponse::new);
    }
    
    @Transactional(readOnly = true)
    public FileDownloadResponse getFileForDownload(Long fileId) {
        MaterialFile file = materialFileRepository.findByIdWithContent(fileId)
                .orElseThrow(() -> new ValidationException("File not found"));
        
        if (file.getFileContent() == null) {
            throw new ValidationException("File content not found");
        }
        
        // Increment download count (in separate transaction)
        incrementDownloadCount(fileId);
        
        Resource resource = new ByteArrayResource(file.getFileContent());
        return new FileDownloadResponse(resource, file.getName(), file.getFileType());
    }
    
    @Transactional
    public void incrementDownloadCount(Long fileId) {
        materialFileRepository.findById(fileId).ifPresent(file -> {
            file.setDownloadCount(file.getDownloadCount() + 1);
            materialFileRepository.save(file);
        });
    }
    
    public void deleteFile(Long fileId) {
        MaterialFile file = materialFileRepository.findById(fileId)
                .orElseThrow(() -> new ValidationException("File not found"));
        
        materialFileRepository.delete(file);
        log.info("File deleted: {} (ID: {})", file.getName(), fileId);
    }
    
    public MaterialFileResponse setPrimaryFile(Long fileId) {
        MaterialFile file = materialFileRepository.findById(fileId)
                .orElseThrow(() -> new ValidationException("File not found"));
        
        // Unset other primary files for this material
        unsetPrimaryFile(file.getMaterialId());
        
        // Set this file as primary
        file.setIsPrimary(true);
        MaterialFile updatedFile = materialFileRepository.save(file);
        
        return new MaterialFileResponse(updatedFile);
    }
    
    private void unsetPrimaryFile(Long materialId) {
        materialFileRepository.findPrimaryByMaterialIdWithoutContent(materialId)
                .ifPresent(primaryFile -> {
                    // Load the full entity to update it
                    materialFileRepository.findById(primaryFile.getId()).ifPresent(fullFile -> {
                        fullFile.setIsPrimary(false);
                        materialFileRepository.save(fullFile);
                    });
                });
    }
    
    
}