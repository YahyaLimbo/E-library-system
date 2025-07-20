package com.mycompany.treviska;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class CreateLoanRequest {
    @NotNull(message = "Material ID is required")
    private Long materialId;
    
    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;
    
    private String notes;
}