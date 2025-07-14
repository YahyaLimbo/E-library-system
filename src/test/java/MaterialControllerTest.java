package com.mycompany.treviska.Controller;

import com.mycompany.treviska.MaterialService;
import com.mycompany.treviska.MaterialFileService;
import com.mycompany.treviska.MaterialResponse;
import com.mycompany.treviska.MaterialSummary;
import com.mycompany.treviska.CreateMaterialRequest;
import com.mycompany.treviska.UpdateMaterialRequest;
import com.mycompany.treviska.MaterialSearchRequest;
import com.mycompany.treviska.MaterialFileResponse;
import com.mycompany.treviska.FileUploadRequest;
import com.mycompany.treviska.FileDownloadResponse;
import com.mycompany.treviska.PageableResponse;
import com.mycompany.treviska.Material;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Integration tests for Material-related API endpoints
 * Tests CRUD operations, search functionality, file management, authentication, and validation
 */
@WebMvcTest(MaterialController.class)
public class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaterialService materialService;

    @MockBean
    private MaterialFileService materialFileService;

    private MaterialResponse testMaterialResponse;
    private MaterialSummary testMaterialSummary;
    private CreateMaterialRequest testCreateRequest;
    private UpdateMaterialRequest testUpdateRequest;
    private MaterialSearchRequest testSearchRequest;

    @BeforeEach
    void setUp() {
        // Set up test data for material operations
    }

    // ===============================================
    // Material CRUD Operations - Public Endpoints
    // ===============================================

    @Test
    void testGetMaterialPublic_Success() {
        // Test public access to material by ID
        // Should return 200 OK with material data (no auth required)
    }

    @Test
    void testGetMaterialPublic_NotFound() {
        // Test public access to non-existent material
        // Should return 404 Not Found
    }

    @Test
    void testGetAllMaterialsPublic_Success() {
        // Test public access to all materials with pagination
        // Should return 200 OK with paginated material summaries
    }

    @Test
    void testGetAllMaterialsPublic_WithPagination() {
        // Test public materials endpoint with custom pagination
        // Should return correct page size and number
    }

    @Test
    void testGetAllMaterialsPublic_WithSorting() {
        // Test public materials endpoint with sorting
        // Should return materials in specified order
    }

    @Test
    void testSearchMaterialsPublic_Success() {
        // Test public material search functionality
        // Should return matching materials
    }

    @Test
    void testSearchMaterialsPublic_NoResults() {
        // Test public search with no matching results
        // Should return empty page
    }

    // ===============================================
    // Material CRUD Operations - Authenticated Endpoints
    // ===============================================

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterial_AuthenticatedUser_Success() {
        // Test authenticated access to material by ID
        // Should return 200 OK with material data
    }

    @Test
    void testGetMaterial_Unauthenticated() {
        // Test access to authenticated material endpoint without auth
        // Should return 401 Unauthorized
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterial_AuthenticatedUser_NotFound() {
        // Test authenticated access to non-existent material
        // Should return 404 Not Found
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetAllMaterials_AuthenticatedUser_Success() {
        // Test authenticated access to all materials
        // Should return 200 OK with paginated results
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAllMaterials_AdminUser_Success() {
        // Test admin access to all materials
        // Should return 200 OK with full access
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialsCustom_Success() {
        // Test custom materials endpoint with PageableResponse
        // Should return materials in custom response format
    }

    // ===============================================
    // Material Creation Operations
    // ===============================================

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_User_Success() {
        // Test material creation by regular user
        // Should return 201 Created with created material
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateMaterial_Admin_Success() {
        // Test material creation by admin user
        // Should return 201 Created with created material
    }

    @Test
    void testCreateMaterial_Unauthenticated() {
        // Test material creation without authentication
        // Should return 401 Unauthorized
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_InvalidInput_MissingTitle() {
        // Test material creation with missing title
        // Should return 400 Bad Request with validation errors
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_InvalidInput_MissingAuthor() {
        // Test material creation with missing author
        // Should return 400 Bad Request with validation errors
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_InvalidInput_MissingIdentifier() {
        // Test material creation with missing identifier
        // Should return 400 Bad Request with validation errors
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_InvalidInput_InvalidType() {
        // Test material creation with invalid material type
        // Should return 400 Bad Request with validation errors
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_InvalidInput_TitleTooLong() {
        // Test material creation with title exceeding 100 characters
        // Should return 400 Bad Request with validation errors
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_DuplicateIdentifier() {
        // Test material creation with existing identifier
        // Should return 400 Bad Request with conflict error
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_ServiceException() {
        // Test material creation when service throws exception
        // Should return 500 Internal Server Error
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterial_ReturnsCorrectJSON() {
        // Test material creation returns proper JSON structure
        // Should include all material fields in response
    }

    // ===============================================
    // Material Update Operations
    // ===============================================

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateMaterial_Admin_Success() {
        // Test material update by admin user
        // Should return 200 OK with updated material
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testUpdateMaterial_User_Forbidden() {
        // Test material update by regular user (should be admin-only)
        // Should return 403 Forbidden
    }

    @Test
    void testUpdateMaterial_Unauthenticated() {
        // Test material update without authentication
        // Should return 401 Unauthorized
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateMaterial_NotFound() {
        // Test updating non-existent material
        // Should return 404 Not Found
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateMaterial_InvalidInput() {
        // Test material update with invalid data
        // Should return 400 Bad Request with validation errors
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateMaterial_PartialUpdate() {
        // Test partial material update (only some fields)
        // Should update only provided fields
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateMaterial_ServiceException() {
        // Test material update when service throws exception
        // Should return appropriate error response
    }

    // ===============================================
    // Material Delete Operations
    // ===============================================

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteMaterial_Admin_Success() {
        // Test material deletion by admin user
        // Should return 204 No Content
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testDeleteMaterial_User_Forbidden() {
        // Test material deletion by regular user (should be admin-only)
        // Should return 403 Forbidden
    }

    @Test
    void testDeleteMaterial_Unauthenticated() {
        // Test material deletion without authentication
        // Should return 401 Unauthorized
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteMaterial_NotFound() {
        // Test deleting non-existent material
        // Should return 404 Not Found
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteMaterial_ServiceException() {
        // Test material deletion when service throws exception
        // Should return appropriate error response
    }

    // ===============================================
    // Material Search Operations
    // ===============================================

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchMaterials_Success() {
        // Test authenticated material search
        // Should return matching materials
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchMaterials_NoResults() {
        // Test search with no matching results
        // Should return empty page
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchMaterials_WithPagination() {
        // Test search with pagination parameters
        // Should return correct page of results
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchMaterials_WithSorting() {
        // Test search with sorting parameters
        // Should return sorted results
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchByTitle_Success() {
        // Test search by title endpoint
        // Should return materials matching title
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchByAuthor_Success() {
        // Test search by author endpoint
        // Should return materials by specified author
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchByPublisher_Success() {
        // Test search by publisher endpoint
        // Should return materials from specified publisher
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testSearchInSummary_Success() {
        // Test search in summary/description endpoint
        // Should return materials with matching summary content
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialsByType_Success() {
        // Test filtering materials by type
        // Should return materials of specified type
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialsByType_InvalidType() {
        // Test filtering by invalid material type
        // Should return 400 Bad Request
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialsByFormat_Success() {
        // Test filtering materials by format
        // Should return materials of specified format
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialsByFormat_InvalidFormat() {
        // Test filtering by invalid material format
        // Should return 400 Bad Request
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetRecentMaterials_Success() {
        // Test getting recent materials
        // Should return recently created materials
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialByIdentifier_Success() {
        // Test finding material by identifier
        // Should return material with matching identifier
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialByIdentifier_NotFound() {
        // Test finding material by non-existent identifier
        // Should return 404 Not Found
    }

    // ===============================================
    // Material Statistics Operations
    // ===============================================

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetMaterialStats_Admin_Success() {
        // Test getting material statistics as admin
        // Should return 200 OK with statistics data
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMaterialStats_User_Forbidden() {
        // Test getting material statistics as regular user
        // Should return 403 Forbidden (admin-only)
    }

    @Test
    void testGetMaterialStatsPublic_Success() {
        // Test getting public material statistics
        // Should return basic statistics without authentication
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetMaterialStats_ServiceException() {
        // Test statistics endpoint when service throws exception
        // Should return appropriate error response
    }

    // ===============================================
    // Bulk Operations
    // ===============================================

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateMaterialsBulk_Admin_Success() {
        // Test bulk material creation by admin
        // Should return 201 Created with list of created materials
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateMaterialsBulk_User_Forbidden() {
        // Test bulk creation by regular user (should be admin-only)
        // Should return 403 Forbidden
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateMaterialsBulk_InvalidInput() {
        // Test bulk creation with invalid materials
        // Should handle errors gracefully and return partial results
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateMaterialsBulk_EmptyList() {
        // Test bulk creation with empty list
        // Should return appropriate response
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetMyMaterials_Success() {
        // Test getting current user's materials
        // Should return materials created by current user
    }

    // ===============================================
    // File Management Operations
    // ===============================================

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUploadFile_Admin_Success() {
        // Test file upload by admin user
        // Should return 201 Created with file information
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testUploadFile_User_Forbidden() {
        // Test file upload by regular user (should be admin-only)
        // Should return 403 Forbidden
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUploadFile_EmptyFile() {
        // Test uploading empty file
        // Should return 400 Bad Request
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUploadFile_FileTooLarge() {
        // Test uploading file exceeding size limit
        // Should return 400 Bad Request
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUploadFile_MaterialNotFound() {
        // Test uploading file for non-existent material
        // Should return 404 Not Found
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUploadFile_PrimaryFile() {
        // Test uploading file marked as primary
        // Should set file as primary for material
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetFilesByMaterial_Success() {
        // Test getting files for a material
        // Should return list of material files
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetFilesByMaterial_MaterialNotFound() {
        // Test getting files for non-existent material
        // Should return appropriate response
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testDownloadFile_Success() {
        // Test downloading file
        // Should return file content with appropriate headers
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testDownloadFile_NotFound() {
        // Test downloading non-existent file
        // Should return 404 Not Found
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testPreviewFile_Success() {
        // Test previewing file
        // Should return file content for inline display
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testPreviewFile_NotFound() {
        // Test previewing non-existent file
        // Should return 404 Not Found
    }

    // ===============================================
    // Security & Authorization Tests
    // ===============================================

    @Test
    void testEndpointSecurity_AuthenticationRequired() {
        // Test that protected endpoints require authentication
        // Should return 401 for unauthenticated requests
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testEndpointSecurity_UserRolePermissions() {
        // Test USER role permissions across all endpoints
        // Should allow read operations, deny admin operations
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testEndpointSecurity_AdminRolePermissions() {
        // Test ADMIN role permissions across all endpoints
        // Should allow all operations
    }

    @Test
    void testEndpointSecurity_PublicEndpointsAccess() {
        // Test that public endpoints don't require authentication
        // Should allow access without authentication
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testRoleBasedAccess_CreateMaterial() {
        // Test role-based access for material creation
        // Should verify proper role requirements
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testRoleBasedAccess_UpdateMaterial() {
        // Test role-based access for material updates
        // Should verify admin-only access
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testRoleBasedAccess_DeleteMaterial() {
        // Test role-based access for material deletion
        // Should verify admin-only access
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testRoleBasedAccess_FileUpload() {
        // Test role-based access for file uploads
        // Should verify admin-only access
    }

    // ===============================================
    // Input Validation & Security Tests
    // ===============================================

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testInputValidation_SQLInjectionAttempt() {
        // Test material operations against SQL injection
        // Should handle malicious input safely
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testInputValidation_XSSAttempt() {
        // Test material operations against XSS attacks
        // Should escape or reject script content
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testInputValidation_SpecialCharacters() {
        // Test handling of special characters in material data
        // Should handle special characters appropriately
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testInputValidation_UnicodeCharacters() {
        // Test handling of Unicode characters
        // Should support international characters
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testInputValidation_LongTextFields() {
        // Test validation of text field lengths
        // Should enforce maximum length constraints
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testInputValidation_InvalidEnumValues() {
        // Test validation of enum fields (type, format)
        // Should reject invalid enum values
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testInputValidation_DateValidation() {
        // Test validation of date fields
        // Should handle various date formats and edge cases
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testFileUploadValidation_FileTypes() {
        // Test file upload validation for different file types
        // Should handle various MIME types appropriately
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testFileUploadValidation_Filename() {
        // Test file upload validation for filenames
        // Should handle special characters in filenames
    }

    // ===============================================
    // Error Handling Tests
    // ===============================================

    @Test
    void testErrorHandling_InvalidHTTPMethod() {
        // Test endpoints with wrong HTTP methods
        // Should return 405 Method Not Allowed
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testErrorHandling_InvalidContentType() {
        // Test endpoints with wrong Content-Type
        // Should return 415 Unsupported Media Type
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testErrorHandling_MalformedJSON() {
        // Test endpoints with malformed JSON
        // Should return 400 Bad Request with clear error message
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testErrorHandling_ServiceUnavailable() {
        // Test error response when service is unavailable
        // Should return 503 Service Unavailable
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testErrorHandling_ValidationErrors() {
        // Test error response format for validation errors
        // Should return structured validation error messages
    }

    // ===============================================
    // Response Format Tests
    // ===============================================

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testResponseFormat_JSONStructure() {
        // Test that responses have correct JSON structure
        // Should include all expected fields
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testResponseFormat_PaginationMetadata() {
        // Test that paginated responses include metadata
        // Should include page number, size, total elements, etc.
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testResponseFormat_TimestampFormat() {
        // Test that timestamp fields are formatted correctly
        // Should use consistent date/time format
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testResponseFormat_EnumSerialization() {
        // Test that enum fields are serialized correctly
        // Should use proper enum value representation
    }

    // ===============================================
    // Performance Tests
    // ===============================================

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testPerformance_LargeResultSet() {
        // Test endpoint performance with large result sets
        // Should handle large responses efficiently
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testPerformance_ComplexSearch() {
        // Test performance of complex search operations
        // Should complete searches within reasonable time
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testPerformance_BulkOperations() {
        // Test performance of bulk material creation
        // Should handle bulk operations efficiently
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testPerformance_FileUpload() {
        // Test performance of file upload operations
        // Should handle file uploads efficiently
    }
}