package com.mycompany.treviska;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor
    public class UpdateLoanRequest {  // FIXED: Removed () after class name
        private LocalDate dueDate;  // FIXED: Changed from dueToDate to dueDate
        private Loan.LoanStatus status;
        private String notes;
    }
    