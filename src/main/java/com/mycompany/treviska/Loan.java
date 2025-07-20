package com.mycompany.treviska;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="material_loans")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loanid")
    private Long loanId;
    
    @Column(name="userid", nullable = false)
    private Long userId;
    
    @Column(name="materialid", nullable = false) 
    private Long materialId;
    
    @Column(name="due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name="return_date")
    private LocalDate returnDate;
    
    @Column(name="borrow_date", nullable = false)
    private LocalDate borrowDate;
    
    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.ACTIVE;
    
    @CreatedDate
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name="notes")
    private String notes;
    
    // Add relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materialid", insertable = false, updatable = false)
    private Material material;
    
    public enum LoanStatus {
        ACTIVE, RETURNED, OVERDUE, LOST
    }
    
    // Helper methods
    public boolean isOverdue() {
        return status == LoanStatus.ACTIVE && 
               dueDate != null && 
               dueDate.isBefore(LocalDate.now());
    }
    
    public int getDaysOverdue() {
        if (!isOverdue()) return 0;
        return (int) java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }
    
    // Manual Builder Pattern
    public static LoanBuilder builder() {
        return new LoanBuilder();
    }
    
    public static class LoanBuilder {
        private Long userId;
        private Long materialId;
        private LocalDate dueDate;
        private LocalDate returnDate;
        private LocalDate borrowDate;
        private LoanStatus status = LoanStatus.ACTIVE;
        private String notes;
        
        public LoanBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        
        public LoanBuilder materialId(Long materialId) {
            this.materialId = materialId;
            return this;
        }
        
        public LoanBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }
        
        public LoanBuilder returnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }
        
        public LoanBuilder borrowDate(LocalDate borrowDate) {
            this.borrowDate = borrowDate;
            return this;
        }
        
        public LoanBuilder status(LoanStatus status) {
            this.status = status;
            return this;
        }
        
        public LoanBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }
        
        public Loan build() {
            Loan loan = new Loan();
            loan.setUserId(this.userId);
            loan.setMaterialId(this.materialId);
            loan.setDueDate(this.dueDate);
            loan.setReturnDate(this.returnDate);
            loan.setBorrowDate(this.borrowDate);
            loan.setStatus(this.status);
            loan.setNotes(this.notes);
            return loan;
        }
    }
}