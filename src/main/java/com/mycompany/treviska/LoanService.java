package com.mycompany.treviska;

import com.mycompany.treviska.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;
    
    private static final int DEFAULT_LOAN_DURATION_DAYS = 14;
    private static final int MAXIMUM_LOAN_NUMBER = 3;
    
    public LoanResponse createLoan(Long userId, CreateLoanRequest request) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found"));
        
        // Validate material exists
        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new ValidationException("Material not found"));
        
        // Check if user has reached maximum loans
       // Long activeLoans = loanRepository.countActiveLoansForUser(userId);
        //if (activeLoans >= MAXIMUM_LOAN_NUMBER) {
         //   throw new ValidationException("Maximum number of active loans exceeded");
       // }
        
        // Check if material is already on loan
        //if (loanRepository.isMaterialCurrentlyLoaned(request.getMaterialId())) {
          //  throw new ValidationException("Material is currently on loan");
        //}
        
        // Create loan entity using manual builder
        Loan loan = Loan.builder()
                .userId(userId)
                .materialId(request.getMaterialId())
                .borrowDate(LocalDate.now())
                .dueDate(request.getDueDate() != null ? request.getDueDate() : 
                         LocalDate.now().plusDays(DEFAULT_LOAN_DURATION_DAYS))
                .status(Loan.LoanStatus.ACTIVE)
                .notes(request.getNotes())
                .build();
        
        Loan savedLoan = loanRepository.save(loan);
        log.info("Loan created successfully: {}", savedLoan.getLoanId());
        
        return new LoanResponse(savedLoan, user, material);
    }
    
    public LoanResponse returnLoan(Long loanId, ReturnLoanRequest request) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ValidationException("Loan not found"));
        
        if (loan.getStatus() != Loan.LoanStatus.ACTIVE) {
            throw new ValidationException("Loan is not active");
        }
        
        // Set return date and status
        loan.setReturnDate(request != null && request.getActualReturnDate() != null ? 
                          request.getActualReturnDate() : LocalDate.now());
        loan.setStatus(Loan.LoanStatus.RETURNED);
        
        if (request != null && request.getNotes() != null) {
            loan.setNotes(request.getNotes());
        }
        
        Loan updatedLoan = loanRepository.save(loan);
        log.info("Loan returned successfully: {}", loanId);
        
        // Load related entities for response
        User user = userRepository.findById(loan.getUserId()).orElse(null);
        Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
        
        return new LoanResponse(updatedLoan, user, material);
    }
    
    @Transactional(readOnly = true)
    public LoanResponse getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ValidationException("Loan not found"));
        
        User user = userRepository.findById(loan.getUserId()).orElse(null);
        Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
        
        return new LoanResponse(loan, user, material);
    }
    
   // @Transactional(readOnly = true)
  //  public List<LoanResponse> getActiveLoansForUser(Long userId) {
     //   List<Loan> loans = loanRepository.findActiveLoansForUser(userId);
        
       // return loans.stream()
         //       .map(loan -> {
           //         Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
             //       return new LoanResponse(loan, null, material);
               // })
          //      .collect(Collectors.toList());
   // }
    
    //@Transactional(readOnly = true)
  //  public Page<LoanResponse> getLoanHistoryForUser(Long userId, Pageable pageable) {
//        Page<Loan> loanPage = loanRepository.findLoanHistoryForUser(userId, pageable);
        
      //  return loanPage.map(loan -> {
          //  Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
        //    return new LoanResponse(loan, null, material);
        //});
   // }
    
    //public LoanResponse renewLoan(Long loanId, int additionalDays) {
      //  Loan loan = loanRepository.findById(loanId)
        //        .orElseThrow(() -> new ValidationException("Loan not found"));
        
        //if (loan.getStatus() != Loan.LoanStatus.ACTIVE) {
          //  throw new ValidationException("Can only renew active loans");
        //}
        
        // Check if loan is already overdue
//        if (loan.isOverdue()) {
  //          throw new ValidationException("Cannot renew overdue loan");
    //    }
        
        // Extend due date
     //   loan.setDueDate(loan.getDueDate().plusDays(additionalDays));
        
       // Loan updatedLoan = loanRepository.save(loan);
       // log.info("Loan renewed successfully: {} for {} days", loanId, additionalDays);
        
       // User user = userRepository.findById(loan.getUserId()).orElse(null);
        //Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
      //  
     // //  return new LoanResponse(updatedLoan, user, material);
   // }
    
  //  @Transactional(readOnly = true)
//    public List<LoanResponse> getOverdueLoans() {
    //    List<Loan> loans = loanRepository.findOverdueLoans();
        
      //  return loans.stream()
        //        .map(loan -> {
          //          User user = userRepository.findById(loan.getUserId()).orElse(null);
            ///        Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
               //     return new LoanResponse(loan, user, material);
               // })
               // .collect(Collectors.toList());
   // }
    
    public LoanResponse updateLoanStatus(Long loanId, Loan.LoanStatus newStatus, String notes) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ValidationException("Loan not found"));
        
        loan.setStatus(newStatus);
        if (notes != null) {
            loan.setNotes(notes);
        }
        
        Loan updatedLoan = loanRepository.save(loan);
        log.info("Loan status updated: {} to {}", loanId, newStatus);
        
        User user = userRepository.findById(loan.getUserId()).orElse(null);
        Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
        
        return new LoanResponse(updatedLoan, user, material);
    }
}