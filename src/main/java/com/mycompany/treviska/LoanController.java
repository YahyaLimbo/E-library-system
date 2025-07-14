package com.mycompany.treviska;
import com.mycompany.treviska.security.Permissions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
@Slf4j
@CrossOrigin(origins = "*")  
public class LoanController {
    private LoanRepository loanRepository;
    private USerRepository userRepository;
    
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity <LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request,
            Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new ValidationException("USer not found"));
    }
    //from here vibe coded
    //return a loan
    @PostMapping("/{loanId}/return")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity <LoanResponse> ReturnLoan(@PathVariable Long loanId, @RequestBody(required=false)ReturnLoanRequest request,
            Authentication authentication) {
        LoanResponse loan = loanService.getLoanById(loanId);
         User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ValidationException("User not found"));
    }
     // Get loan by ID
    @GetMapping("/{loanId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<LoanResponse> getLoan(
            @PathVariable Long loanId,
            Authentication authentication) {
    try {
            LoanResponse loan = loanService.getLoanById(loanId);
            
            // Check if user can view this loan
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new ValidationException("User not found"));
            
            if (!loan.getUserId().equals(user.getUserid()) && 
                !authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            return ResponseEntity.ok(loan);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Get current user's active loans
    @GetMapping("/my-loans/active")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<LoanResponse>> getMyActiveLoans(Authentication authentication) {
        
        log.info("User '{}' requesting their active loans", authentication.getName());
        
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ValidationException("User not found"));
        
        List<LoanResponse> loans = loanService.getActiveLoansForUser(user.getUserid());
        return ResponseEntity.ok(loans);
    }
 
    // Get current user's loan history
    @GetMapping("/my-loans/history")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<Page<LoanResponse>> getMyLoanHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "borrowDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            Authentication authentication) {
        
        log.info("User '{}' requesting their loan history", authentication.getName());
        
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ValidationException("User not found"));
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<LoanResponse> loans = loanService.getLoanHistoryForUser(user.getUserid(), pageable);
        return ResponseEntity.ok(loans);
    }
    
    // Renew a loan
    @PostMapping("/{loanId}/renew")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<LoanResponse> renewLoan(
            @PathVariable Long loanId,
            @RequestParam(defaultValue = "7") int additionalDays,
            Authentication authentication) {
        
        log.info("User '{}' requesting to renew loan {} for {} days", 
                authentication.getName(), loanId, additionalDays);
        
        // Verify user owns the loan
        LoanResponse loan = loanService.getLoanById(loanId);
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ValidationException("User not found"));
        
        if (!loan.getUserId().equals(user.getUserid()) && 
            !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            LoanResponse renewedLoan = loanService.renewLoan(loanId, additionalDays);
            return ResponseEntity.ok(renewedLoan);
        } catch (ValidationException e) {
            log.warn("Failed to renew loan: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
