package org.alain.library.api.model.loan;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@NaturalIdCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Status {

    @Id
    @Enumerated(EnumType.STRING)
    private StatusDesignation designation;

    @OneToMany(mappedBy = "status")
    private List<LoanStatus> loanStatuses = new ArrayList<>();

    public Status() {
    }

    public Status(StatusDesignation designation, List<LoanStatus> loanStatuses) {
        this.designation = designation;
        this.loanStatuses = loanStatuses;
    }

    public StatusDesignation getDesignation() {
        return designation;
    }

    public void setDesignation(StatusDesignation designation) {
        this.designation = designation;
    }

    public List<LoanStatus> getLoanStatuses() {
        return loanStatuses;
    }

    public void setLoanStatuses(List<LoanStatus> loanStatuses) {
        this.loanStatuses = loanStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return designation == status.designation &&
                Objects.equals(loanStatuses, status.loanStatuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(designation, loanStatuses);
    }
}
