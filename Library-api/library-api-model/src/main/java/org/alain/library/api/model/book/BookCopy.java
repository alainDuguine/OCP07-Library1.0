package org.alain.library.api.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alain.library.api.model.loan.Loan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;

    @ManyToOne
    private Book book;

    @OneToMany(mappedBy = "bookCopy")
    private List<Loan> loanList = new ArrayList<>();

}
