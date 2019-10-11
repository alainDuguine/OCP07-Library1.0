package org.alain.library.api.model.loan;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;


/**
 * Composite Primary Key to the intermediary table between
 * Loan and Status
 */

@Embeddable
public class LoanStatusId implements Serializable {

    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "status_designation")
    @Enumerated(EnumType.STRING)
    private StatusDesignation statusDesignation;

    public LoanStatusId() {
    }

    public LoanStatusId(Long loanId, StatusDesignation statusDesignation) {
        this.loanId = loanId;
        this.statusDesignation = statusDesignation;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public StatusDesignation getStatusDesignation() {
        return statusDesignation;
    }

    public void setStatusDesignation(StatusDesignation statusDesignation) {
        this.statusDesignation = statusDesignation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanStatusId that = (LoanStatusId) o;
        return Objects.equals(loanId, that.loanId) &&
                statusDesignation == that.statusDesignation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, statusDesignation);
    }
}
