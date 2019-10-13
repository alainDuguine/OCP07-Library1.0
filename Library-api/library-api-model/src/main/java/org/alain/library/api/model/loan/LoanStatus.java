package org.alain.library.api.model.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Represents the association table between Loan and Status,
 * which has as primary key the object @Embeddable LoanStatusId
 */
@Entity(name = "LoanStatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_status")
public class LoanStatus {

    @EmbeddedId
    private LoanStatusId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("loanId")
    @JoinColumn(name="loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("statusId")
    @JoinColumn(name="status_id")
    private Status status;

    @Column
    private LocalDate date = LocalDate.now();

    public LoanStatus(Loan loan, Status status, LocalDate date) {
        this.loan = loan;
        this.status = status;
        this.date = date;
    }
}
