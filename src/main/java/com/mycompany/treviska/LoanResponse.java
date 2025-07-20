package com.mycompany.treviska;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
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
    
    // Constructor from Loan entity
    public LoanResponse(Loan loan) {
        this.loanId = loan.getLoanId();
        this.materialId = loan.getMaterialId();
        this.userId = loan.getUserId();
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
    
    // Constructor with full details
    public LoanResponse(Loan loan, User user, Material material) {
        this(loan);
        if (user != null) {
            this.username = user.getUsername();
        }
        if (material != null) {
            this.materialTitle = material.getTitle();
            this.materialAuthor = material.getAuthor();
        }
    }
}