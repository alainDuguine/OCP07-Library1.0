package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.SharedBookAuthorManagement;
import org.alain.library.api.consumer.repository.AuthorRepository;
import org.alain.library.api.consumer.repository.BookRepository;
import org.alain.library.api.model.book.Author;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SharedBookAuthorManagementImpl implements SharedBookAuthorManagement {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public SharedBookAuthorManagementImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> getAuthorByFullName(String firstName, String lastName){
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public void deleteBookWithEmptyAuthors(Long id) {
        bookRepository.deleteById(id);
    }

}
