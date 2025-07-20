
package com.mycompany.treviska;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//JpaRepository CRUD

@Repository
public interface  MaterialTagRepository extends JpaRepository<Tags, Long>{
    Optional<Tags> findByMaterialId(Long materialId);
    List<Tags> findByType(Long materialId);
    List<Tags> deleteByMaterialId(Long materialId);
    List<Tags> updateByMatrialid(Long materialId);
    
    @Query("SELECT t FROM Tags t WHERE JSON_EXTRACT(t.tags, '$') LIKE %:tag%")
    List<Tags> findByTags(@Param("tag")String Tag);
    
    @Query("SELECT COUNT(t) FROM Tags t WHERE JSON_EXTRACT(t.tags,'$') LIKE %:tag%")
    List<Tags> countByTags(@Param("tag")String Tag);
    
    
}
