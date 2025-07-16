package com.mycompany.treviska;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanDto {
    
    // Create, update and return request DTOs
    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor
    public static class CreateLoanRequest{  // FIXED: Removed () after class name
        @NotNull(message = "Material ID is required")
        private Long materialId;
        
        @NotNull(message = "Due date is required")
        @Future(message = "Due date must be in the future")
        private LocalDate dueDate;  // FIXED: Changed from dueToDate to dueDate
        
        private String notes;
    }
    
    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor
    public static class UpdateLoanRequest {  // FIXED: Removed () after class name
        private LocalDate dueDate;  // FIXED: Changed from dueToDate to dueDate
        private Loan.LoanStatus status;
        private String notes;
    }
    
    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor
    public static class ReturnLoanRequest {  // FIXED: Removed () after class name
        private String notes;
    }
    
    @Data 
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanResponse {
        private Long loanId;
        private Long userId;
        private String username;
        private Long materialId;
        private String materialTitle;
        private String materialAuthor;
        private LocalDate borrowDate;
        private LocalDate dueDate;
        private LocalDate returnDate;
        private Loan.LoanStatus status;
        private String notes;
        private boolean isOverdue;
        private int daysOverdue;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // FIXED: Moved constructors inside the LoanResponse class
        // Constructor from Loan entity
        public LoanResponse(Loan loan) {
            this.loanId = loan.getLoanId();  // FIXED: Correct field names
            this.materialId = loan.getMaterialId();  // FIXED: Correct field names
            this.userId = loan.getUserId();  // FIXED: Correct field names
            this.borrowDate = loan.getBorrowDate();
            this.dueDate = loan.getDueDate();
            this.returnDate = loan.getReturnDate();
            this.status = loan.getStatus();
            this.notes = loan.getNotes();
            this.isOverdue = loan.isOverdue();
            this.daysOverdue = loan.getDaysOverdue();
            this.createdAt = loan.getCreatedAt();
            this.updatedAt = loan.getUpdatedAt();
        }
        
        // Constructor with full details (including user and material info)
        public LoanResponse(Loan loan, User user, Material material) {
            this(loan); // Call the basic constructor
            if (user != null) {
                this.username = user.getUsername();
            }
            if (material != null) {
                this.materialTitle = material.getTitle();
                this.materialAuthor = material.getAuthor();
            }
        }
    }
    
    // Optional: Add a summary DTO for lists
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanSummary {
        private Long loanId;
        private String materialTitle;
        private LocalDate borrowDate;
        private LocalDate dueDate;
        private Loan.LoanStatus status;
        private boolean isOverdue;
        
        public LoanSummary(Loan loan, Material material) {
            this.loanId = loan.getLoanId();
            this.materialTitle = material != null ? material.getTitle() : "Unknown";
            this.borrowDate = loan.getBorrowDate();
            this.dueDate = loan.getDueDate();
            this.status = loan.getStatus();
            this.isOverdue = loan.isOverdue();
        }
    }
}  // FIXED: Removed extra closing braces