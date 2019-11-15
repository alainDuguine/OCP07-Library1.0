package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.LoanManagement;
import org.alain.library.api.business.contract.UserManagement;
import org.alain.library.api.business.exceptions.*;
import org.alain.library.api.consumer.repository.*;
import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.model.loan.Loan;
import org.alain.library.api.model.loan.LoanStatus;
import org.alain.library.api.model.loan.Status;
import org.alain.library.api.model.loan.StatusDesignation;
import org.alain.library.api.model.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanManagementImpl extends CrudManagementImpl<Loan> implements LoanManagement {

    private final LoanRepository loanRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final UserManagement userManagement;
    private final BookCopyRepository bookCopyRepository;

    public LoanManagementImpl(LoanRepository loanRepository, LoanStatusRepository loanStatusRepository, UserRepository userRepository, StatusRepository statusRepository, UserManagement userManagement, BookCopyRepository bookCopyRepository) {
        super(loanRepository);
        this.loanRepository = loanRepository;
        this.loanStatusRepository = loanStatusRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.userManagement = userManagement;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public List<Loan> findLoans(String status, Long user) {
        return loanRepository.findByCurrentStatusAndUserId(status, user);
    }

    @Override
    public List<Loan> getLoansByUserId(Long userId, String authorization) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            String userCredentials = user.get().getEmail() + ':' + user.get().getPassword();
            if (userManagement.checkUserCredentialsFromB64Encoded(userCredentials, authorization)) {
                return loanRepository.findLoansByUserId(userId);
            }else{
                throw new UnauthorizedException("You are not allowed to acces these user's loans");
            }
        }else{
            throw new UnknownUserException("User n°" + userId + " doesn't exist");
        }
    }

    @Override
    public List<LoanStatus> getLoanStatusList(Long id) {
        if(loanRepository.findById(id).isPresent()) {
            return loanStatusRepository.findAllByLoanId(id);
        }else{
            throw new UnknownLoanException("Loan n°" + id + " doesn't exist");
        }
    }

    @Override
    public Loan createNewLoan(Long bookCopyId, Long userId) {
        Optional<BookCopy> bookCopy = bookCopyRepository.findById(bookCopyId);
//        Optional<User> user = userRepository.findById(userId);
//        if (!bookCopy.isPresent()) {
//            throw new UnknownBookCopyException("BookCopy n°" + bookCopyId + " doesn't exist");
//        } else
        if (!bookCopy.get().isAvailable()) {
            throw new BookCopyNotAvailableException("BookCopy n°" + bookCopyId + " is not available");
        }
//        if (!user.isPresent()) {
//            throw new UnknownUserException("User n°" + userId + " doesn't exist");
//        }
        Loan loan = new Loan();
        loan.setBookCopy(bookCopy.get());
//        loan.setBookCopy(BookCopy.builder().id(bookCopyId).build());
        loan.setUser(User.builder().id(userId).build());
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusWeeks(4));
        try {
            this.addLoanStatusToLoan(loan, StatusDesignation.LOANED);
        }catch (Exception e){
            throw new UnknownParameterException(String.format("Cannot create loan for bookCopy %d and user %d", bookCopyId, userId),e);
        }
        return loan;
    }

    @Override
    public Optional<LoanStatus> updateLoan(Long id, String status) {
        try {
            Optional<Loan> loan = loanRepository.findById(id);
            StatusDesignation statusDesignation = StatusDesignation.valueOf(status.toUpperCase());
            if(loan.isPresent()) {
                return Optional.of(this.addLoanStatusToLoan(loan.get(), statusDesignation));
            }else{
                return Optional.empty();
            }
        }catch(IllegalArgumentException ex){
            throw new UnknowStatusException("The status "+status+" doesn't exists.");
        }

    }

    private LoanStatus addLoanStatusToLoan(Loan loan, StatusDesignation statusDesignation) {
        Status status = statusRepository.findStatusByDesignation(statusDesignation);
        loan.getBookCopy().setAvailable(statusDesignation == StatusDesignation.RETURNED);
        loan.setCurrentStatus(status.getDesignation().toString());
        LoanStatus loanStatus = loan.addLoanStatus(status);
        loanRepository.save(loan);
        return loanStatus;
    }

    @Override
    public Optional<LoanStatus> extendLoan(Long id, String authorization) {
        Optional<Loan> loan = loanRepository.findById(id);
        if (loan.isPresent()){
            Optional<User> user = userRepository.findById(loan.get().getUser().getId());
            if(user.isPresent()){
                String userCredentials = user.get().getEmail() + ':' + user.get().getPassword();
                if(userManagement.checkUserCredentialsFromB64Encoded(userCredentials, authorization)){
                    if(!loan.get().getCurrentStatus().equals("PROLONGED")){
                        loan.get().setEndDate(loan.get().getEndDate().plusWeeks(4));
                        return Optional.of(this.addLoanStatusToLoan(loan.get(), StatusDesignation.PROLONGED));
                    }else{
                        return Optional.empty();
                    }
                }else{
                    throw new UnauthorizedException();
                }
            }else{
                throw new UnknownUserException("User n°" + loan.get().getUser().getId() + " doesn't exist");
            }
        }else{
            throw new UnknownLoanException("Loan n°" + id + " doesn't exist");
        }
    }
}
