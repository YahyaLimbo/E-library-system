package com.mycompany.treviska;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanDto {
    
    //create, update and return request
    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor
    public static class CreateLoanRequest() {
    private Long materialId;
    private LocalDate dueToDate;
    private String notes;
    }
    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor
    public static class UpdateLoanRequest() {
        private LocalDate dueToDate;  
        private Loan.LoanStatus status;
        private String notes;
    }
    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor
    public static class ReturnLoanRequest(){
        private String notes;
    }
    
    
    @Data 
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanResponse{
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
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
    
    public LoanResponse(Loan loan){
        this.loanid = loan.getLoanid();
        this.materiald = loan.getmaterialid();
        this.userid = loan.getUserid();
        this.loanid = loan.getLoanid();
        this.borrowDate = loan.getBorrowDate();
        this.dueDate = loan.getDueDate();
        this.returnDate = loan.getReturnDate();
        this.status = loan.getStatus();
        this.notes = loan.getNotes();
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
