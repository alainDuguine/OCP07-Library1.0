package org.alain.library.api.model.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "LoanStatus")
@Table(name = "loan_status")
public class LoanStatus {

    @EmbeddedId
    private LoanStatusId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("loanId")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("statusDesignation")
    private Status status;

    @Column
    private LocalDate date = LocalDate.now();
}
