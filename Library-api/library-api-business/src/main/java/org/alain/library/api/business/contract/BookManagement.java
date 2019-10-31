package org.alain.library.api.business.contract;

import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;

import java.util.List;
import java.util.Set;

public interface BookManagement extends CrudManager<Book>{
    List<Book> findByTitle(String title);
//    boolean checkIfBookExists(List<Book> bookList, BookForm bookForm);
    Book saveNewBook(Book book, Set<Author> authors);
    Book updateBook(Long id, Book book, Set<Author> authors);
//    Book updateBook(Long id, BookDto book, Set<Author> authors);
}
