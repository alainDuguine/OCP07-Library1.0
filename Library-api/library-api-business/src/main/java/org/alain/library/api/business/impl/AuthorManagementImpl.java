package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.AuthorManagement;
import org.alain.library.api.consumer.repository.AuthorRepository;
import org.alain.library.api.consumer.repository.BookRepository;
import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorManagementImpl extends CrudManagementImpl<Author> implements AuthorManagement {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorManagementImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> findAuthorsByName(String name){
        return authorRepository.findAuthorsListByName(name);
    }

    @Override
    public Optional<Author> saveAuthor(Author author) {
        if (this.findAuthorsByName(author.getFirstName()+ ' ' + author.getLastName()).isEmpty()){
            author.setBooks(new HashSet<>());
            return Optional.of(authorRepository.save(author));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> updateAuthor(Long id, Author authorForm) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            author.get().setFirstName(authorForm.getFirstName());
            author.get().setLastName(authorForm.getLastName());
            return Optional.of(authorRepository.save(author.get()));
        }
        return Optional.empty();
    }

    @Override
    public void deleteAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            for (Book book : new HashSet<>(author.get().getBooks())) {
                author.get().removeBook(book);
                if(book.getAuthors().isEmpty()){
                    bookRepository.deleteById(book.getId());
                }
            }
            authorRepository.delete(author.get());
        }
    }
}