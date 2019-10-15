package org.alain.library.api.webservice;

import org.alain.library.api.consumer.repository.BookCopyRepository;
import org.alain.library.api.consumer.repository.BookRepository;
import org.alain.library.api.consumer.repository.StatusRepository;
import org.alain.library.api.consumer.repository.UserRepository;
import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;
import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.model.loan.Status;
import org.alain.library.api.model.loan.StatusDesignation;
import org.alain.library.api.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatusRepository statusRepository;

    @Autowired
    public DbInit(UserRepository userRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, PasswordEncoder passwordEncoder, StatusRepository statusRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        /*=======================================================================================
        ========================== USER TEST ====================================================
        =======================================================================================*/

        User user1 = new User("alain_duguine@hotmail.fr", passwordEncoder.encode("1234"), "Alain", "Duguine", "USER","");
        User user2 = new User("audrey.duguine@gmail.com", passwordEncoder.encode("1234"),"Audrey", "Duguine", "USER","");
        userRepository.save(user1);
        userRepository.save(user2);

        user1.setFirstName("Jean Louis");

        userRepository.save(user1);

        /*=======================================================================================
        ========================== BOOK TEST ====================================================
        =======================================================================================*/

        Book book1 = new Book("20 000 lieues sous les mers", new Author("Jules", "Verne"));
        Book book2 = new Book("Les trois mousquetaires", new Author("Alexandre", "Dumas"));
        Book book3 = new Book("Roméo et Juliette", new Author("William", "Shakespeare"));

        book1.addCopy(new BookCopy());
        book1.addCopy(new BookCopy());
        book1.addCopy(new BookCopy());

        book2.addCopy(new BookCopy());
        book2.addCopy(new BookCopy());
        book2.addCopy(new BookCopy());
        book2.addCopy(new BookCopy());

        book3.addCopy(new BookCopy());
        book3.addCopy(new BookCopy());
        book3.addCopy(new BookCopy());
        book3.addCopy(new BookCopy());
        book3.addCopy(new BookCopy());

        List<Book> books = Arrays.asList(book1,book2,book3);

        bookRepository.saveAll(books);

        /*=======================================================================================
        ========================== STATUS COLLECTION SAVE =======================================
        =======================================================================================*/

        Status statusLoaned = new Status(StatusDesignation.LOANED);
        Status statusProlonged = new Status(StatusDesignation.PROLONGED);
        Status statusReturned = new Status(StatusDesignation.RETURNED);
        Status statusLate = new Status(StatusDesignation.LATE);
        Status statusClosed = new Status(StatusDesignation.CLOSED);

        List<Status> listStatuses = Arrays.asList(statusLoaned,statusProlonged,statusReturned,statusLate,statusClosed);

        statusRepository.saveAll(listStatuses);

    }
}
