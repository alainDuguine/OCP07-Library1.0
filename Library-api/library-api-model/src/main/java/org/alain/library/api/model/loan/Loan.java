package org.alain.library.api.model.loan;


import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
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

    @OneToMany(mappedBy = "loan")
    private List<LoanStatus> loanStatuses = new ArrayList<>();

    public Loan() {
    }

    public Loan(LocalDate startDate, LocalDate endDate, boolean returned, BookCopy bookCopy, User user, List<LoanStatus> loanStatuses) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = returned;
        this.bookCopy = bookCopy;
        this.user = user;
        this.loanStatuses = loanStatuses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Loan loan = (Loan) o;
        return returned == loan.returned &&
                Objects.equals(id, loan.id) &&
                Objects.equals(startDate, loan.startDate) &&
                Objects.equals(endDate, loan.endDate) &&
                Objects.equals(bookCopy, loan.bookCopy) &&
                Objects.equals(user, loan.user) &&
                Objects.equals(loanStatuses, loan.loanStatuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, returned, bookCopy, user, loanStatuses);
    }
}
