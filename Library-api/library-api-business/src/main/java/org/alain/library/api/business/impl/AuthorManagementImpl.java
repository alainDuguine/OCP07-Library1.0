package org.alain.library.api.business.impl;


import org.alain.library.api.business.contract.AuthorManagement;
import org.alain.library.api.consumer.repository.AuthorRepository;
import org.alain.library.api.model.book.Author;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorManagementImpl extends CrudManagerImpl<Author> implements AuthorManagement {

    private final AuthorRepository authorRepository;

    public AuthorManagementImpl(AuthorRepository authorRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAuthorsByName(String name){
        return authorRepository.findAuthorsListByName(name);
    }

    @Override
    public Optional<Author> saveAuthor(Author author) {
        if (this.findAuthorsByName(author.getFirstName()+ ' ' + author.getLastName()).isEmpty()){
            return Optional.empty();
        }
        author.setBooks(new HashSet<>());
        return Optional.of(authorRepository.save(author));
    }

    @Override
    public Optional<Author> update(Long id, Author authorForm) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            author.get().setFirstName(authorForm.getFirstName());
            author.get().setLastName(authorForm.getLastName());
            return Optional.of(authorRepository.save(author.get()));
        }
        return Optional.empty();
    }
}
