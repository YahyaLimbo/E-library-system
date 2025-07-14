package com.mycompany.treviska;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional

@Repository
public interface Loanrepository extends JpaRepository<Loan, Long> {
    
    //find all the loans by userid page
    Page<Loan> findByUserId(Long userid, Pageable pageable);
    
    //find all loands by materialid
    Page<Loan> findByMateiralId(Long matererialid, Pageable pageable);
    
    //find user with loan active status
    @Query("SELEC m FROM materials_loan m WHERE userid = userid AND m status = 'active';")
    Page<Loan> findUserByStatusActive(Long userid); //might use @Param('userId')
    //find materials with loan active status
    @Query("SELEC m FROM materials_loan m WHERE materialid =:materialid AND m status = 'active';")
    Page<Loan> findMaterialByStatusActive(Long materialid); //might use @Param('materialId')
    @Query("SELECT m FROM materials_loan m WHERE m status ='overdue' AND m returned_date IS NULL")
    Page<Loan>findMaterialOverdue(Long materialid);
    
    
    
}
