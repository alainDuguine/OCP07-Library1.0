package org.alain.library.api.consumer.repository;

import org.alain.library.api.model.loan.LoanStatus;
import org.alain.library.api.model.loan.LoanStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanStatusRepository extends JpaRepository<LoanStatus, LoanStatusId> {

    Optional<LoanStatus> findFirstByLoanIdOrderByDateDesc(Long id);
    List<LoanStatus> findAllByLoanId(Long id);

}
