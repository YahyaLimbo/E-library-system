package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    
    private final TestFileRepository testFileRepository;
    
    @PostMapping("/upload")
    public ResponseEntity<String> testUpload(@RequestParam("file") MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            
            System.out.println("=== TEST DEBUG ===");
            System.out.println("File size: " + file.getSize());
            System.out.println("Bytes length: " + fileBytes.length);
            System.out.println("Bytes class: " + fileBytes.getClass().getName());
            
            TestFileEntity entity = TestFileEntity.builder()
                    .name(file.getOriginalFilename())
                    .content(fileBytes)
                    .build();
            
            System.out.println("Entity content class: " + entity.getContent().getClass().getName());
            System.out.println("Entity content length: " + entity.getContent().length);
            
            TestFileEntity saved = testFileRepository.save(entity);
            
            return ResponseEntity.ok("SUCCESS: Saved with ID " + saved.getId());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }
}