package com.mycompany.treviska;

import com.mycompany.treviska.security.Permissions;
import com.mycompany.treviska.Repository.UserRepository;
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
@RequestMapping("/api/loans")
@Slf4j
@CrossOrigin(origins = "*")
public class LoanController {
    
    private final LoanService loanService;
    private final UserRepository userRepository;
    
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<LoanResponse> createLoan(
            @Valid @RequestBody CreateLoanRequest request,
            Authentication authentication) {
        
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ValidationException("User not found"));
        
        try {
            LoanResponse loan = loanService.createLoan(user.getUserId(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(loan);
        } catch (ValidationException e) {
            log.warn("Failed to create loan: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{loanId}/return")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public ResponseEntity<LoanResponse> returnLoan(
            @PathVariable Long loanId, 
            @RequestBody(required = false) ReturnLoanRequest request,
            Authentication authentication) {
        
        try {
            // Verify user owns the loan or is admin
            LoanResponse loan = loanService.getLoanById(loanId);
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new ValidationException("User not found"));
            
            if (!loan.getUserId().equals(user.getUserId()) && 
                !authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            LoanResponse returnedLoan = loanService.returnLoan(loanId, request);
            return ResponseEntity.ok(returnedLoan);
        } catch (ValidationException e) {
            log.warn("Failed to return loan: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
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
            
            if (!loan.getUserId().equals(user.getUserId()) && 
                !authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            return ResponseEntity.ok(loan);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        }
    }
}