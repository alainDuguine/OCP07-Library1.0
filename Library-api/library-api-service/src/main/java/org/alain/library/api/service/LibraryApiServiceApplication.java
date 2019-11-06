package org.alain.library.api.service;

import org.alain.library.api.consumer.repository.*;
import org.alain.library.api.model.loan.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"org.alain.library.api"})
@EntityScan(basePackages = {"org.alain.library.api"})
@EnableJpaRepositories(basePackages = {"org.alain.library.api"})
@EnableSwagger2
public class LibraryApiServiceApplication implements CommandLineRunner {

    private StatusRepository statusRepository;

    public LibraryApiServiceApplication(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

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
//
//        User user = new User();
//        user.setEmail("alain_duguine@hotmail.fr");
//        user.setPassword("admin");
//        user.setPasswordConfirmation("admin");
//        user.setFirstName("Alain");
//        user.setLastName(("Duguine"));
//        userRepository.save(user);
//
//        Author author = new Author();
//        author.setFirstName("Victor");
//        author.setLastName("Hugo");
//        authorRepository.save(author);
//
//        Book book = new Book();
//        book.setTitle("Notre dame de Paris");
//        book.addAuthor(author);
//        bookRepository.save(book);
//
//        BookCopy bookCopy = new BookCopy();
//        bookCopy.setEditor("Editions du seuil");
//        bookCopy.setBook(book);
//        bookCopyRepository.save(bookCopy);
//
//        // Création prêt 1
//
//        Loan loan = new Loan();
//        loan.setBookCopy(bookCopy);
//        loan.setUser(user);
//        loan.setStartDate(LocalDate.now());
//        loan.setEndDate(loan.getStartDate().plusWeeks(4));
//
//        Status status = (statusRepository.findStatusByDesignation(StatusDesignation.LOANED));
//
//        LoanStatus loanStatus = new LoanStatus(loan, status);
//        loan.getLoanStatuses().add(loanStatus);
//        status.getLoanStatuses().add(loanStatus);
//
//        loanRepository.save(loan);
////
//        // Création prêt 2
//
//        Loan loan2 = new Loan();
//        loan2.setBookCopy(bookCopy);
//        loan2.setUser(user);
//        loan2.setEndDate(loan.getStartDate().plusWeeks(4));
//
//        Status status2 = (statusRepository.findStatusByDesignation(StatusDesignation.RETURNED));
//
//        LoanStatus loanStatus2 = new LoanStatus(loan2, status2);
//        loan2.getLoanStatuses().add(loanStatus2);
//        status2.getLoanStatuses().add(loanStatus2);
//
//        LoanStatus loanStatus3 = new LoanStatus(loan2, status);
//        loanStatus3.setDate(LocalDate.now().minusWeeks(2));
//        loan2.getLoanStatuses().add(loanStatus3);
//        status.getLoanStatuses().add(loanStatus3);
//
//        Status status3 = (statusRepository.findStatusByDesignation(StatusDesignation.LATE));
//
//        LoanStatus loanStatus4 = new LoanStatus(loan2, status3);
//        loanStatus3.setDate(LocalDate.now().plusWeeks(2));
//        loan2.getLoanStatuses().add(loanStatus4);
//        status3.getLoanStatuses().add(loanStatus4);
//
//
//        loanRepository.save(loan2);

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
