package org.alain.library.api.web;

import org.alain.library.api.consumer.*;
import org.alain.library.api.model.user.Role;
import org.alain.library.api.model.user.RoleDesignation;
import org.alain.library.api.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = "org.alain.library.api.*")
@EntityScan("org.alain.library.api.model.*")
@EnableJpaRepositories(basePackages = "org.alain.library.api.consumer")
public class LibraryApiWebApplication implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(LibraryApiWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Role user = new Role(RoleDesignation.USER);
        Role admin = new Role(RoleDesignation.ADMIN);

        roleRepository.save(user);
        roleRepository.save(admin);

        User user1 = new User("alain_duguine@hotmail.fr", encoder.encode("1234"), user);
        User user2 = new User("audrey.duguine@gmail.com", encoder.encode("1234"), user);

        user1.addRole(admin);

        userRepository.save(user1);
        userRepository.save(user2);

    }
}
