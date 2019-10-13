package org.alain.library.api.model.loan;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

}
