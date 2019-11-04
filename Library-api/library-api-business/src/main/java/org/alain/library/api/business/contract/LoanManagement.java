package org.alain.library.api.business.contract;

import org.alain.library.api.model.loan.Loan;
import org.alain.library.api.model.loan.LoanStatus;

import java.util.List;
import java.util.Optional;

public interface LoanManagement extends CrudManagement<Loan> {
    Optional<LoanStatus> getCurrentStatus(Loan loan);
    List<Loan> findAllByStatus(String status);
}
