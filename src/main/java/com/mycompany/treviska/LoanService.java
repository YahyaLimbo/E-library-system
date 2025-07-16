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
    
    private static final int MaximumLoanDays =7;
    private static final int MaximumLoanNumber = 3;
    
    public LoanDto.LoanResponse createLoan(Long userId,LoanDto.CreateLoanRequest request){
        User user =UserRepository.findbyId(userId);
        Material material =MaterialRepository.findbyId();   
        Long activeLoans= loanRepository.countActiveLoansUser(userid);
        if(activeLoans>MaximumLoanNumber){
        throw new ValidationException("Number of loans exceeded");
        }
        //Loan entity
    Loan loan = loan.builder()
            .userId(userId)
                .materialId(request.getMaterialId())
                .borrowDate(LocalDate.now())
                .dueDate(request.getDueDate() != null ? request.getDueDate() : 
                         LocalDate.now().plusDays(DEFAULT_LOAN_DURATION_DAYS))
                .status(Loan.LoanStatus.ACTIVE)
                .notes(request.getNotes())
                .build();
    Loan loanSaved = loanRepository.save(loan);
    return new LoanDto.LoanResponse(loanSaved,user, material);
    }
    
   public LoanDto.LoanResponse ReturnLoan(Long loanid, LoanDto.ReturnLoanRequest request){
       Loan loan =LoanRepository.findByLoan(loanid)
               .orElseThrow(() -> new ValidationException("Loan not found"));
       
       Loan.setReturnDate(LocalDateTime.now());
       Loan.setStatus(Status.RETURNED);
       Loan updatedLoan = loanRepository.save(loan);
       User user = userRepository.findById(loan.getUserId()).orElse(null);
       Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
        
       return new LoanDto.LoanResponse(updatedLoan, user, material);
   }
   //remember to fetch through obj
   @Transactional(readOnly= true)
   public LoanResponse FindByLoanId(Long loanId){
       Loan loan = loanReposiroty.findById(loanId)
               .orElseThrow(()->new ValidationException("Loan not found"));
       User user = userRepository.findById(loan.getUserId()).orElse(null);
        Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
        
        return new LoanResponse(loan, user, material);
}
   //get users who has active loans
   @Transactional(readOnly=true)
   public List<LoanDto.LoanResponse> GetActiveLoansList(Long userid){
       List<Loan> loans= LoanRepository.findActiveLoanUsers(userid);
       
       return loans.stream()
                .map(loan -> {
                    Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
                    return new LoanDto.LoanResponse(loan, null, material);
                })
                .collect(Collectors.toList());
   }
    @Transactional(readOnly=true)
    public LoanDto.LoanResponse UpdateLoanStatus(Long loanid, Loan.LoanStatus newStatus, String notes){
        Loan loan = loanRepository.findById(loanid)
                .orElseThrow(()->new ValidationException("id not found"));
        loan.setStatut(newStatus);
        
        Loan Updatedloan = loanRepository.save(loan);
        User user = userRepository.findById(loan.getUserId()).orElse(null);
        Material material = materialRepository.findById(loan.getMaterialId()).orElse(null);
        
        return new LoanDto.LoanResponse(Updatedloan, user, material);
    }
   
}

