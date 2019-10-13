package org.alain.library.api.model.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * Composite Primary Key to the intermediary table between
 * Loan and Status
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class LoanStatusId implements Serializable {

    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "status_id")
    private Long statusId;

}
