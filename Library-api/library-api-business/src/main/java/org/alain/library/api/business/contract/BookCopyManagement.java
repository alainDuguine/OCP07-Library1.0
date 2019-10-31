package org.alain.library.api.business.contract;

import org.alain.library.api.model.book.Book;
import org.alain.library.api.model.book.BookCopy;

import java.util.List;

public interface BookCopyManagement extends CrudManager<BookCopy>{
    void deleteCopy(BookCopy bookCopy);
    void saveNewCopy(BookCopy bookCopy, Book book);
    List<BookCopy> findCopiesFromBook(Long id);
}
