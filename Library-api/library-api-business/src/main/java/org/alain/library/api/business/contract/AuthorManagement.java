package org.alain.library.api.business.contract;

import org.alain.library.api.model.book.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorManagement extends CrudManager<Author>{

    List<Author> findAuthorsByName(String name);
    Optional<Author> saveAuthor(Author author);
    Optional<Author> update(Long id, Author author);
}
