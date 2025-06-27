package com.mycompany.treviska;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    
    // Find by identifier (unique field in your schema)
    Optional<Material> findByIdentifier(String identifier);
    
    // Check if identifier exists
    boolean existsByIdentifier(String identifier);
    
    // Find by type
    List<Material> findByType(Material.MaterialType type);
    
    // Find by format
    List<Material> findByFormat(Material.MaterialFormat format);
    
    // Search by title (case-insensitive)
    @Query("SELECT m FROM Material m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Material> findByTitleContainingIgnoreCase(@Param("title") String title);
    
    // Search by author (case-insensitive)
    @Query("SELECT m FROM Material m WHERE LOWER(m.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Material> findByAuthorContainingIgnoreCase(@Param("author") String author);
    
    // Search by publisher
    @Query("SELECT m FROM Material m WHERE LOWER(m.publisher) LIKE LOWER(CONCAT('%', :publisher, '%'))")
    List<Material> findByPublisherContainingIgnoreCase(@Param("publisher") String publisher);
    
    // Search by language
    List<Material> findByLanguageIgnoreCase(String language);
    
    // Complex search query
    @Query("SELECT m FROM Material m WHERE " +
           "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(m.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:publisher IS NULL OR LOWER(m.publisher) LIKE LOWER(CONCAT('%', :publisher, '%'))) AND " +
           "(:type IS NULL OR m.type = :type) AND " +
           "(:format IS NULL OR m.format = :format) AND " +
           "(:language IS NULL OR LOWER(m.language) = LOWER(:language))")
    Page<Material> searchMaterials(
        @Param("title") String title,
        @Param("author") String author, 
        @Param("publisher") String publisher,
        @Param("type") Material.MaterialType type,
        @Param("format") Material.MaterialFormat format,
        @Param("language") String language,
        Pageable pageable);
    
    // Find materials by publication date range
    List<Material> findByDatePublishedBetween(LocalDate startDate, LocalDate endDate);
    
    // Find materials published in a specific year
    @Query("SELECT m FROM Material m WHERE YEAR(m.datePublished) = :year")
    List<Material> findByPublicationYear(@Param("year") int year);
    
    // Find materials by multiple types
    List<Material> findByTypeIn(List<Material.MaterialType> types);
    
    // Find materials by multiple formats
    List<Material> findByFormatIn(List<Material.MaterialFormat> formats);
    
    // Count materials by type
    @Query("SELECT m.type, COUNT(m) FROM Material m GROUP BY m.type")
    List<Object[]> countMaterialsByType();
    
    // Count materials by format
    @Query("SELECT m.format, COUNT(m) FROM Material m GROUP BY m.format")
    List<Object[]> countMaterialsByFormat();
    
    // Find recent materials (within last 30 days)
    @Query("SELECT m FROM Material m WHERE m.createdAt >= :thirtyDaysAgo ORDER BY m.createdAt DESC")
    List<Material> findRecentMaterials(@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo);
    
    // Search in summary/description
    @Query("SELECT m FROM Material m WHERE LOWER(m.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Material> findBySummaryContainingIgnoreCase(@Param("keyword") String keyword);
}