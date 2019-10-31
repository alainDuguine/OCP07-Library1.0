package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.BookCopyManagement;
import org.alain.library.api.consumer.repository.BookCopyRepository;
import org.alain.library.api.model.book.Book;
import org.alain.library.api.model.book.BookCopy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyManagementImpl extends CrudManagerImpl<BookCopy> implements BookCopyManagement {

    private final BookCopyRepository bookCopyRepository;

    public BookCopyManagementImpl(BookCopyRepository bookCopyRepository) {
        super(bookCopyRepository);
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public void deleteCopy(BookCopy bookCopy) {
        bookCopy.getBook().removeCopy(bookCopy);
        bookCopyRepository.delete(bookCopy);
    }

    @Override
    public void saveNewCopy(BookCopy bookCopy, Book book) {
        book.addCopy(bookCopy);
        bookCopyRepository.save(bookCopy);
    }

    @Override
    public List<BookCopy> findCopiesFromBook(Long id) {
        return bookCopyRepository.findAllByBookId(id);
    }
}
