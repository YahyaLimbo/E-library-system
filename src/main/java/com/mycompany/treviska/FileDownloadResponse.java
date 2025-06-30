package com.mycompany.treviska;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;

@Data
@AllArgsConstructor
public class FileDownloadResponse {
    private Resource resource;
    private String fileName;
    private String mimeType;
}