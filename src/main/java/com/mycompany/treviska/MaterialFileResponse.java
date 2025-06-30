package com.mycompany.treviska;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialFileResponse {
    private Long id;
    private Long materialId;
    private String name;
    private String fileType;
    private Long fileSize;
    private Integer downloadCount;
    private Boolean isPrimary;
    private LocalDateTime uploadedAt;
    
    public MaterialFileResponse(MaterialFile file) {
        this.id = file.getId();
        this.materialId = file.getMaterialId();
        this.name = file.getName();
        this.fileType = file.getFileType();
        this.fileSize = file.getFileSize();
        this.downloadCount = file.getDownloadCount();
        this.isPrimary = file.getIsPrimary();
        this.uploadedAt = file.getUploadedAt();
    }
}
