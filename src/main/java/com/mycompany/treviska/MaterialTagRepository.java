
package com.mycompany.treviska;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
//JpaRepository CRUD

@Repository
public interface  MaterialTagRepository extends JpaRepository<Tags, Long>{
    Optional<Tags> findByMaterialId(Long materialId);
    List<Tags> findByType(String tags);
    void deleteByMaterialId(Long materialId);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE materialtags SET tags = ?2::jsonb WHERE materialid = ?1", nativeQuery = true)     
    Long updateByMaterialId(Long materialId, String tagsJson);
    
   @Query(value = "SELECT * FROM materialtags WHERE tags::text ILIKE CONCAT('%\"', ?1, '\"%')", nativeQuery = true)
    List<Tags> findByTags(String Tag);
    
     // Count using the same safe approach
   @Query(value = "SELECT COUNT(DISTINCT t.materialtagid) FROM materialtags t, jsonb_array_elements_text(t.tags) AS tag_element WHERE tag_element = ?1", nativeQuery = true)
    Long countByTags(String Tag);
    
    
}
