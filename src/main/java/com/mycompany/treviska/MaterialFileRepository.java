package com.mycompany.treviska;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialFileRepository extends JpaRepository<MaterialFile, Long> {
    
    // Find all files for a specific material (without file content for performance)
    @Query("SELECT new MaterialFile(f.id, f.materialId, f.name, f.fileSize, f.fileType, " +
           "f.downloadCount, f.isPrimary, f.uploadedAt, f.updatedAt) " +
           "FROM MaterialFile f WHERE f.materialId = :materialId " +
           "ORDER BY f.isPrimary DESC, f.uploadedAt DESC")
    List<MaterialFile> findByMaterialIdWithoutContent(@Param("materialId") Long materialId);
    
    // Find primary file for a material (without content)
    @Query("SELECT new MaterialFile(f.id, f.materialId, f.name, f.fileSize, f.fileType, " +
           "f.downloadCount, f.isPrimary, f.uploadedAt, f.updatedAt) " +
           "FROM MaterialFile f WHERE f.materialId = :materialId AND f.isPrimary = true")
    Optional<MaterialFile> findPrimaryByMaterialIdWithoutContent(@Param("materialId") Long materialId);
    
    // Find file with content for download
    @Query("SELECT f FROM MaterialFile f WHERE f.id = :fileId")
    Optional<MaterialFile> findByIdWithContent(@Param("fileId") Long fileId);
    
    // Count files for a material
    @Query("SELECT COUNT(f) FROM MaterialFile f WHERE f.materialId = :materialId")
    Long countFilesByMaterialId(@Param("materialId") Long materialId);
    
    // Get total storage used by a material
    @Query("SELECT COALESCE(SUM(f.fileSize), 0) FROM MaterialFile f WHERE f.materialId = :materialId")
    Long getTotalFileSizeByMaterialId(@Param("materialId") Long materialId);
    
    // Find files by type (without content)
    @Query("SELECT new MaterialFile(f.id, f.materialId, f.name, f.fileSize, f.fileType, " +
           "f.downloadCount, f.isPrimary, f.uploadedAt, f.updatedAt) " +
           "FROM MaterialFile f WHERE f.fileType = :fileType")
    List<MaterialFile> findByFileTypeWithoutContent(@Param("fileType") String fileType);
    
    // Check if primary file exists for material
    boolean existsByMaterialIdAndIsPrimaryTrue(Long materialId);
}