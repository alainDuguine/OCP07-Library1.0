package org.alain.library.api.model.loan;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents the association table between Loan and Status,
 * which has as primary key the object @Embeddable LoanStatusId
 */
@Entity(name = "LoanStatus")
@Table(name = "loan_status")
public class LoanStatus {

    @EmbeddedId
    private LoanStatusId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("loanId")
    @JoinColumn(name="loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("statusDesignation")
    @JoinColumn(name="status_designation")
    private Status status;

    @Column
    private LocalDate date = LocalDate.now();

    public LoanStatus() {
    }

    public LoanStatus(LoanStatusId id, Loan loan, Status status, LocalDate date) {
        this.id = id;
        this.loan = loan;
        this.status = status;
        this.date = date;
    }

    public LoanStatusId getId() {
        return id;
    }

    public void setId(LoanStatusId id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanStatus that = (LoanStatus) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(loan, that.loan) &&
                Objects.equals(status, that.status) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loan, status, date);
    }
}
