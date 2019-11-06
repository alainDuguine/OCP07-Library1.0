package org.alain.library.api.consumer.repository;

import org.alain.library.api.model.loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findLoansByCurrentStatus(String status);
    List<Loan> findLoansByUserId(Long id);
}
