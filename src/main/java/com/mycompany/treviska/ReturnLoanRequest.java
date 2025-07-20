package com.mycompany.treviska;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class ReturnLoanRequest {
    private String notes;
    private LocalDate actualReturnDate;
}

