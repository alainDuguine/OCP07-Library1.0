package org.alain.library.api.business.contract;

import org.alain.library.api.model.book.Author;

import java.util.Optional;

public interface SharedBookAuthorManagement {
    Optional<Author> getAuthorByFullName(String firstName, String lastName);
    void deleteBookWithEmptyAuthors(Long id);
}
