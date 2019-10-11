package org.alain.library.api.consumer;

import org.alain.library.api.model.loan.LoanStatus;
import org.alain.library.api.model.loan.LoanStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStatusRepository extends JpaRepository<LoanStatus, LoanStatusId> {
}
