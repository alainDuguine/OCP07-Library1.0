package org.alain.library.api.model.book;

import lombok.Data;
import org.alain.library.api.model.loan.Loan;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private String editor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @OneToMany(mappedBy = "bookCopy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loanList = new ArrayList<>();

    public BookCopy() {
        this.barcode = RandomStringUtils.randomNumeric(13);
    }

    public void addLoan(Loan loan){
        this.loanList.add(loan);
        loan.setBookCopy(this);
    }

    public void removeLoan(Loan loan){
        this.loanList.remove(loan);
        loan.setBookCopy(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        return id != null && id.equals(((BookCopy)o).getId());
    }

    @Override
    public int hashCode() {
        return 29;
    }
}
