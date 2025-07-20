package com.mycompany.treviska;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    // Find all loans by userId with pagination
    Page<Loan> findByUserId(Long userId, Pageable pageable);
    
    // Find all loans by materialId with pagination
    Page<Loan> findByMaterialId(Long materialId, Pageable pageable);
    
    // Find active loans for a user (returns List, not Page)
    @Query("SELECT l FROM Loan l WHERE l.userId = :userId AND l.status = 'ACTIVE'")
    List<Loan> findActiveLoansForUser(@Param("userId") Long userId);
    
    // Find active loans for a material (returns List, not Page)
    @Query("SELECT l FROM Loan l WHERE l.materialId = :materialId AND l.status = 'ACTIVE'")
    List<Loan> findActiveLoansForMaterial(@Param("materialId") Long materialId);
    
    // Find overdue loans (returns List, not Page)
    @Query("SELECT l FROM Loan l WHERE l.status = 'ACTIVE' AND l.dueDate < CURRENT_DATE")
    List<Loan> findOverdueLoans();
    
    // Count active loans for a user
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.userId = :userId AND l.status = 'ACTIVE'")
    Long countActiveLoansForUser(@Param("userId") Long userId);
    
    // Count active loans for a material
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.materialId = :materialId AND l.status = 'ACTIVE'")
    Long countActiveLoansForMaterial(@Param("materialId") Long materialId);
    
    // Find loans by status (returns List, not Page)
    List<Loan> findByStatus(Loan.LoanStatus status);
    
    // Find loans by date range (returns List, not Page)
    List<Loan> findByBorrowDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find loans due soon (returns List, not Page)
    @Query("SELECT l FROM Loan l WHERE l.status = 'ACTIVE' AND l.dueDate BETWEEN CURRENT_DATE AND :dueDate")
    List<Loan> findLoansDueSoon(@Param("dueDate") LocalDate dueDate);
    
    // Check if material is currently on loan
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Loan l WHERE l.materialId = :materialId AND l.status = 'ACTIVE'")
    boolean isMaterialCurrentlyLoaned(@Param("materialId") Long materialId);
    
    // Find loan history for user (returns Page with Pageable parameter)
    @Query("SELECT l FROM Loan l WHERE l.userId = :userId ORDER BY l.createdAt DESC")
    Page<Loan> findLoanHistoryForUser(@Param("userId") Long userId, Pageable pageable);
}
// my errors. wrong select statment with m. , before pagination use @param, 