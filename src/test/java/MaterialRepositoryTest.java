//package com.mycompany.treviska;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
///**
// * Repository layer tests for MaterialRepository
// * Tests custom query methods, search functionality, pagination, and database operations
// */
//@DataJpaTest
//@ContextConfiguration(classes = Treviska.class)
//public class MaterialRepositoryTest {
//
//    @Autowired
//    private MaterialRepository materialRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    private Material book1;
//    private Material book2;
//    private Material journal1;
//    private Material magazine1;
//    private Material thesis1;
//
//    @BeforeEach
//    void setUp() {
//        // Create diverse test materials for comprehensive query testing
//        // This setup will be used across multiple test methods
//    }
//
//    // ===============================================
//    // Basic Repository Operations
//    // ===============================================
//
//    @Test
//    void testSaveAndFindById() {
//        // Test basic save and retrieve operations
//        // Should save material and retrieve by ID successfully
//    }
//
//    @Test
//    void testFindAll() {
//        // Test retrieving all materials
//        // Should return all saved materials
//    }
//
//    @Test
//    void testDeleteById() {
//        // Test deleting material by ID
//        // Should remove material from database
//    }
//
//    @Test
//    void testExistsById() {
//        // Test checking if material exists by ID
//        // Should return true for existing materials, false otherwise
//    }
//
//    @Test
//    void testCount() {
//        // Test counting total materials
//        // Should return correct count of materials in database
//    }
//
//    // ===============================================
//    // Custom Query Methods - Single Field Searches
//    // ===============================================
//
//    @Test
//    void testFindByIdentifier_Found() {
//        // Test finding material by unique identifier
//        // Should return material when identifier exists
//    }
//
//    @Test
//    void testFindByIdentifier_NotFound() {
//        // Test finding material by non-existent identifier
//        // Should return empty Optional
//    }
//
//    @Test
//    void testExistsByIdentifier_True() {
//        // Test checking identifier existence when it exists
//        // Should return true for existing identifier
//    }
//
//    @Test
//    void testExistsByIdentifier_False() {
//        // Test checking identifier existence when it doesn't exist
//        // Should return false for non-existent identifier
//    }
//
//    @Test
//    void testFindByType() {
//        // Test finding materials by type (e.g., book, journal)
//        // Should return all materials of specified type
//    }
//
//    @Test
//    void testFindByFormat() {
//        // Test finding materials by format (print, digital, audiobook)
//        // Should return all materials of specified format
//    }
//
//    @Test
//    void testFindByLanguageIgnoreCase() {
//        // Test finding materials by language (case-insensitive)
//        // Should return materials in specified language regardless of case
//    }
//
//    // ===============================================
//    // Text Search Methods
//    // ===============================================
//
//    @Test
//    void testFindByTitleContainingIgnoreCase_Found() {
//        // Test searching by title substring (case-insensitive)
//        // Should return materials with matching title text
//    }
//
//    @Test
//    void testFindByTitleContainingIgnoreCase_NotFound() {
//        // Test searching by title substring with no matches
//        // Should return empty list
//    }
//
//    @Test
//    void testFindByTitleContainingIgnoreCase_PartialMatch() {
//        // Test searching by partial title match
//        // Should return materials containing the search term
//    }
//
//    @Test
//    void testFindByAuthorContainingIgnoreCase_Found() {
//        // Test searching by author name (case-insensitive)
//        // Should return materials by matching authors
//    }
//
//    @Test
//    void testFindByAuthorContainingIgnoreCase_NotFound() {
//        // Test searching by author name with no matches
//        // Should return empty list
//    }
//
//    @Test
//    void testFindByAuthorContainingIgnoreCase_PartialMatch() {
//        // Test searching by partial author name
//        // Should return materials with authors containing search term
//    }
//
//    @Test
//    void testFindByPublisherContainingIgnoreCase_Found() {
//        // Test searching by publisher name (case-insensitive)
//        // Should return materials from matching publishers
//    }
//
//    @Test
//    void testFindByPublisherContainingIgnoreCase_NotFound() {
//        // Test searching by publisher name with no matches
//        // Should return empty list
//    }
//
//    @Test
//    void testFindBySummaryContainingIgnoreCase_Found() {
//        // Test searching within material summaries
//        // Should return materials with matching summary content
//    }
//
//    @Test
//    void testFindBySummaryContainingIgnoreCase_NotFound() {
//        // Test searching summary with no matches
//        // Should return empty list
//    }
//
//    // ===============================================
//    // Date-based Queries
//    // ===============================================
//
//    @Test
//    void testFindByDatePublishedBetween() {
//        // Test finding materials published within date range
//        // Should return materials published between start and end dates
//    }
//
//    @Test
//    void testFindByDatePublishedBetween_NoResults() {
//        // Test date range query with no matching materials
//        // Should return empty list
//    }
//
//    @Test
//    void testFindByPublicationYear() {
//        // Test finding materials published in specific year
//        // Should return materials from specified year only
//    }
//
//    @Test
//    void testFindByPublicationYear_NoResults() {
//        // Test publication year query with no matches
//        // Should return empty list
//    }
//
//    @Test
//    void testFindRecentMaterials() {
//        // Test finding recently created materials (last 30 days)
//        // Should return materials created within last 30 days
//    }
//
//    @Test
//    void testFindRecentMaterials_NoRecentMaterials() {
//        // Test recent materials query when no recent materials exist
//        // Should return empty list
//    }
//
//    // ===============================================
//    // Multiple Value Queries
//    // ===============================================
//
//    @Test
//    void testFindByTypeIn() {
//        // Test finding materials with multiple types
//        // Should return materials matching any of the specified types
//    }
//
//    @Test
//    void testFindByTypeIn_EmptyList() {
//        // Test finding materials with empty type list
//        // Should return empty results
//    }
//
//    @Test
//    void testFindByFormatIn() {
//        // Test finding materials with multiple formats
//        // Should return materials matching any of the specified formats
//    }
//
//    @Test
//    void testFindByFormatIn_EmptyList() {
//        // Test finding materials with empty format list
//        // Should return empty results
//    }
//
//    // ===============================================
//    // Complex Search Queries
//    // ===============================================
//
//    @Test
//    void testSearchMaterials_AllParametersProvided() {
//        // Test complex search with all search parameters
//        // Should return materials matching all criteria
//    }
//
//    @Test
//    void testSearchMaterials_OnlyTitleProvided() {
//        // Test complex search with only title parameter
//        // Should return materials matching title, ignoring other criteria
//    }
//
//    @Test
//    void testSearchMaterials_OnlyAuthorProvided() {
//        // Test complex search with only author parameter
//        // Should return materials matching author, ignoring other criteria
//    }
//
//    @Test
//    void testSearchMaterials_OnlyTypeProvided() {
//        // Test complex search with only type parameter
//        // Should return materials matching type, ignoring other criteria
//    }
//
//    @Test
//    void testSearchMaterials_MultipleParametersProvided() {
//        // Test complex search with subset of parameters
//        // Should return materials matching all provided criteria
//    }
//
//    @Test
//    void testSearchMaterials_NoParametersProvided() {
//        // Test complex search with all null parameters
//        // Should return all materials (no filtering)
//    }
//
//    @Test
//    void testSearchMaterials_NoResults() {
//        // Test complex search with criteria that match no materials
//        // Should return empty page
//    }
//
//    @Test
//    void testSearchMaterials_CaseInsensitiveSearch() {
//        // Test complex search case insensitivity
//        // Should match regardless of case in text fields
//    }
//
//    // ===============================================
//    // Pagination and Sorting
//    // ===============================================
//
//    @Test
//    void testSearchMaterials_WithPagination() {
//        // Test complex search with pagination
//        // Should return correct page of results
//    }
//
//    @Test
//    void testSearchMaterials_FirstPage() {
//        // Test retrieving first page of search results
//        // Should return first set of results with correct pagination info
//    }
//
//    @Test
//    void testSearchMaterials_LastPage() {
//        // Test retrieving last page of search results
//        // Should return final set of results with correct pagination info
//    }
//
//    @Test
//    void testSearchMaterials_EmptyPage() {
//        // Test retrieving page beyond available results
//        // Should return empty page with correct pagination info
//    }
//
//    @Test
//    void testSearchMaterials_SortByTitle() {
//        // Test search results sorted by title
//        // Should return results in alphabetical order by title
//    }
//
//    @Test
//    void testSearchMaterials_SortByAuthor() {
//        // Test search results sorted by author
//        // Should return results in alphabetical order by author
//    }
//
//    @Test
//    void testSearchMaterials_SortByDatePublished() {
//        // Test search results sorted by publication date
//        // Should return results in chronological order
//    }
//
//    @Test
//    void testSearchMaterials_SortDescending() {
//        // Test search results with descending sort
//        // Should return results in reverse order
//    }
//
//    @Test
//    void testFindAll_WithPageable() {
//        // Test findAll with pagination
//        // Should return paginated results with correct metadata
//    }
//
//    @Test
//    void testFindAll_WithSort() {
//        // Test findAll with sorting
//        // Should return sorted results
//    }
//
//    // ===============================================
//    // Aggregation and Statistics Queries
//    // ===============================================
//
//    @Test
//    void testCountMaterialsByType() {
//        // Test aggregation query counting materials by type
//        // Should return correct counts for each material type
//    }
//
//    @Test
//    void testCountMaterialsByType_EmptyDatabase() {
//        // Test type counting with no materials
//        // Should return empty results
//    }
//
//    @Test
//    void testCountMaterialsByFormat() {
//        // Test aggregation query counting materials by format
//        // Should return correct counts for each format
//    }
//
//    @Test
//    void testCountMaterialsByFormat_EmptyDatabase() {
//        // Test format counting with no materials
//        // Should return empty results
//    }
//
//    // ===============================================
//    // Query Performance Tests
//    // ===============================================
//
//    @Test
//    void testSearchPerformance_LargeDataset() {
//        // Test search performance with large number of materials
//        // Should complete search within reasonable time
//    }
//
//    @Test
//    void testSearchPerformance_ComplexQuery() {
//        // Test performance of complex multi-parameter search
//        // Should handle complex queries efficiently
//    }
//
//    @Test
//    void testIndexUsage_IdentifierSearch() {
//        // Test that identifier searches use database index
//        // Should perform efficiently due to unique constraint
//    }
//
//    @Test
//    void testQueryOptimization_TextSearch() {
//        // Test text search query optimization
//        // Should handle case-insensitive searches efficiently
//    }
//
//    // ===============================================
//    // Edge Cases and Error Handling
//    // ===============================================
//
//    @Test
//    void testSearchMaterials_NullParameters() {
//        // Test complex search with null parameters
//        // Should handle null values gracefully
//    }
//
//    @Test
//    void testSearchMaterials_EmptyStringParameters() {
//        // Test complex search with empty string parameters
//        // Should handle empty strings appropriately
//    }
//
//    @Test
//    void testSearchMaterials_SpecialCharacters() {
//        // Test search with special characters in parameters
//        // Should handle special characters without errors
//    }
//
//    @Test
//    void testSearchMaterials_UnicodeCharacters() {
//        // Test search with Unicode characters
//        // Should handle international characters properly
//    }
//
//    @Test
//    void testSearchMaterials_SQLInjectionAttempt() {
//        // Test search parameters containing SQL injection attempts
//        // Should safely handle malicious input without vulnerability
//    }
//
//    @Test
//    void testFindByIdentifier_NullParameter() {
//        // Test identifier search with null parameter
//        // Should handle null gracefully
//    }
//
//    @Test
//    void testFindByIdentifier_EmptyString() {
//        // Test identifier search with empty string
//        // Should return empty result
//    }
//
//    @Test
//    void testDateQueries_InvalidDateRange() {
//        // Test date range queries with invalid ranges (end before start)
//        // Should handle invalid ranges gracefully
//    }
//
//    @Test
//    void testDateQueries_NullDates() {
//        // Test date queries with null date parameters
//        // Should handle null dates appropriately
//    }
//
//    // ===============================================
//    // Data Consistency Tests
//    // ===============================================
//
//    @Test
//    void testDataConsistency_AfterSave() {
//        // Test that saved data can be immediately retrieved
//        // Should maintain consistency between save and find operations
//    }
//
//    @Test
//    void testDataConsistency_AfterUpdate() {
//        // Test data consistency after updating material
//        // Should reflect changes in subsequent queries
//    }
//
//    @Test
//    void testDataConsistency_AfterDelete() {
//        // Test data consistency after deleting material
//        // Should not return deleted materials in queries
//    }
//
//    @Test
//    void testDataConsistency_TransactionBoundaries() {
//        // Test data visibility across transaction boundaries
//        // Should handle transaction isolation properly
//    }
//
//    // ===============================================
//    // Custom Query Method Validation
//    // ===============================================
//
//    @Test
//    void testCustomQueries_ReturnTypes() {
//        // Test that custom queries return expected types
//        // Should return correct Optional, List, or Page types
//    }
//
//    @Test
//    void testCustomQueries_ParameterBinding() {
//        // Test that query parameters are bound correctly
//        // Should use parameters as intended in queries
//    }
//
//    @Test
//    void testCustomQueries_JPQLSyntax() {
//        // Test that JPQL queries have correct syntax
//        // Should execute without syntax errors
//    }
//
//    @Test
//    void testCustomQueries_CaseInsensitivity() {
//        // Test case insensitive queries work as expected
//        // Should ignore case in text comparisons
//    }
//
//    // ===============================================
//    // Repository Integration Tests
//    // ===============================================
//
//    @Test
//    void testRepositoryIntegration_WithEntityManager() {
//        // Test repository operations work with entity manager
//        // Should maintain consistency with direct entity manager usage
//    }
//
//    @Test
//    void testRepositoryIntegration_LazyLoading() {
//        // Test lazy loading behavior in repository queries
//        // Should handle lazy relationships appropriately
//    }
//
//    @Test
//    void testRepositoryIntegration_CachingBehavior() {
//        // Test query result caching behavior
//        // Should handle caching correctly if enabled
//    }
//
//    @Test
//    void testRepositoryIntegration_ConnectionPooling() {
//        // Test repository under connection pool constraints
//        // Should handle database connections efficiently
//    }
//}