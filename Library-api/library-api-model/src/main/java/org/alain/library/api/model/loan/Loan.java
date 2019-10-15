package org.alain.library.api.model.loan;


import lombok.*;
import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Loan {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate;
    private boolean returned = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookCopy bookCopy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanStatus> loanStatuses = new ArrayList<>();

    public void addStatus(Status status){
        LoanStatus loanStatus = new LoanStatus(this, status, LocalDate.now());
        this.loanStatuses.add(loanStatus);
        status.getLoanStatuses().add(loanStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        return id != null && id.equals(((Loan)o).getId());
    }

    @Override
    public int hashCode() {
        return 23;
    }

}
