package com.mycompany.treviska;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name="materials_loans")
@Getter @Setter @NoArgsConstructor 
@EntityListeners(AuditingEntityListener.class)
public class Loan{
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 @Column(name = "loanid")
 private long loadid;
 @Column(name="userid")
 private long userid;
 @Column(name="materialid")
 private long materialid;
 @Column(name="due_date")
 private LocalDate due_date;
 @Column (name="return_date")
 private LocalDate return_date;
 @Column (name = "borrow_date")
 private LocalDate borrow_date;
 @Column (name = "status")
 @Enumerated(EnumType.STRING)
 private LoanStatus status;
 @CreatedDate
 @Column (name= "created_at")
 private LocalDateTime created_at;
 @LastModifiedDate
 @Column (name="updated_at")
 private LocalDateTime updated_at;
 @Column (name="notes")
 private String notes;
 public enum LoanStatus{
    active, returned, overdue;
    //write manyyomany relationship user aadn materual
}
} 
 
 