package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.LoanManagement;
import org.alain.library.api.consumer.repository.LoanRepository;
import org.alain.library.api.consumer.repository.LoanStatusRepository;
import org.alain.library.api.model.loan.Loan;
import org.alain.library.api.model.loan.LoanStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanManagementImpl extends CrudManagementImpl<Loan> implements LoanManagement {

    private final LoanRepository loanRepository;
    private final LoanStatusRepository loanStatusRepository;

    public LoanManagementImpl(LoanRepository loanRepository, LoanStatusRepository loanStatusRepository) {
        super(loanRepository);
        this.loanRepository = loanRepository;
        this.loanStatusRepository = loanStatusRepository;
    }

    @Override
    public Optional<LoanStatus> getCurrentStatus(Loan loan) {
        return loanStatusRepository.findFirstByLoanIdOrderByDateDesc(loan.getId());
    }

    @Override
    public List<Loan> findAllByStatus(String status) {
        if (status.isEmpty() || status == null){
            return loanRepository.findAll();
        }
        return loanRepository.findAll();
    }
}
