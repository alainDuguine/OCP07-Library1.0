package org.alain.library.api.model.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = startDate.plusWeeks(4);
    private boolean returned = false;

    @ManyToOne
    private BookCopy bookCopy;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LoanStatus> loanStatuses = new ArrayList<>();

}
