package org.alain.library.api.service;

import org.alain.library.api.consumer.repository.*;
import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;
import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.model.loan.Loan;
import org.alain.library.api.model.loan.LoanStatus;
import org.alain.library.api.model.loan.Status;
import org.alain.library.api.model.loan.StatusDesignation;
import org.alain.library.api.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@SpringBootApplication
@ComponentScan(basePackages = {"org.alain.library.api"})
@EntityScan(basePackages = {"org.alain.library.api"})
@EnableJpaRepositories(basePackages = {"org.alain.library.api"})
@EnableSwagger2
public class LibraryApiServiceApplication implements CommandLineRunner {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiServiceApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (StatusDesignation statusDesignation: StatusDesignation.values()){
            Status status = new Status(statusDesignation);
            statusRepository.save(status);
        }

        User user = new User();
        user.setEmail("alain_duguine@hotmail.fr");
        user.setPassword("admin");
        user.setPasswordConfirmation("admin");
        user.setFirstName("Alain");
        user.setLastName(("Duguine"));
        userRepository.save(user);

        Author author = new Author();
        author.setFirstName("Victor");
        author.setLastName("Hugo");
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Notre dame de Paris");
        book.addAuthor(author);
        bookRepository.save(book);

        BookCopy bookCopy = new BookCopy();
        bookCopy.setEditor("Editions du seuil");
        bookCopy.setBook(book);
        bookCopyRepository.save(bookCopy);

        Loan loan = new Loan();
        loan.setBookCopy(bookCopy);
        loan.setUser(user);
        loan.setEndDate(loan.getStartDate().plusWeeks(4));

        LoanStatus loanStatus = new LoanStatus();
        loanStatus.setStatus(statusRepository.findStatusByDesignation(StatusDesignation.LOANED));

        loan.addLoanStatus(loanStatus);

        loanRepository.save(loan);
    }

//    @Bean
//    public ServletWebServerFactory servletContainer(){
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context){
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//        return tomcat;
//    }
//
//    private Connector httpToHttpsRedirectConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8082);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
}
