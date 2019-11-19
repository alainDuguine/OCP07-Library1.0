package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.BookManagement;
import org.alain.library.api.business.exceptions.UnknownAuthorException;
import org.alain.library.api.business.exceptions.UnknownBookException;
import org.alain.library.api.consumer.repository.AuthorRepository;
import org.alain.library.api.consumer.repository.BookCopyRepository;
import org.alain.library.api.consumer.repository.BookRepository;
import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;
import org.alain.library.api.model.book.BookCopy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookManagementImpl extends CrudManagementImpl<Book> implements BookManagement {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final AuthorRepository authorRepository;

    public BookManagementImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository, AuthorRepository authorRepository) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findByTitle(String title) {
        //        List<Object> resultSet = bookRepository.findBooksByTitleWithCopies("%"+title+"%");
//        List<Book> bookList = new ArrayList<>();
//        for (Object res: resultSet) {
//            Object[] row = (Object[]) res;
//            if(row[2] != null && row[2].equals(true)){
//                Book book = (Book) row[0];
//                book.setNbCopiesAvailable((Long)row[1]);
//                bookList.add(book);
//            }else{
//                if(((Book)row[0]).getCopyList().size() == (Long)row[1]){
//                    Book book = (Book) row[0];
//                    book.setNbCopiesAvailable(0L);
//                    bookList.add(book);
//                }
//            }
//        }
        return bookRepository.findByTitleLike("%"+title+"%");
    }

    @Override
    public Optional<BookCopy> findCopyInBook(Long bookId, Long copyId) {
        return bookCopyRepository.findOneByIdInBook(bookId, copyId);
    }

    @Override
    public List<BookCopy> findCopiesInBook(Long id) {
        if(bookRepository.findById(id).isPresent()) {
            return bookCopyRepository.findAllByBookId(id);
        }else {
            throw new UnknownBookException("The book doesn't exists");
        }
    }

    @Override
    public void deleteCopyInBook(Long bookId, Long copyId) {
        bookCopyRepository.deleteByIdAndAndBookId(copyId, bookId);
    }

    @Override
    public Optional<Book> saveBook(Book book) {
        try {
            this.manageAuthors(book);
        } catch (UnknownAuthorException e) {
            throw new UnknownAuthorException("The author doesn't exist in the database");
        }
        if (bookAlreadyExists(book)) {
            return Optional.empty();
        }else{
            return Optional.of(bookRepository.save(book));
        }
    }

    @Override
    public Optional<Book> updateBook(Long id, Book bookForm) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            try {
                this.manageAuthors(bookForm);
                bookForm.setId(book.get().getId());
            } catch (UnknownAuthorException e) {
                throw new UnknownAuthorException("The author doesn't exist in the database");
            }
            if (bookAlreadyExists(bookForm)) {
                return Optional.empty();
            } else {
                book.get().setTitle(bookForm.getTitle());
                book.get().setIsbn(bookForm.getIsbn());
                if(!book.get().getAuthors().equals(bookForm.getAuthors())) {
                    book.get().setAuthors(bookForm.getAuthors());
                }
                return Optional.of(bookRepository.save(book.get()));
            }
        }else{
            throw new UnknownBookException("The book number"+id+"doesn't exists");
        }
    }

    @Override
    public Optional<BookCopy> saveBookCopy(Long id, BookCopy bookCopy) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            book.get().addCopy(bookCopy);
            return Optional.of(bookCopyRepository.save(bookCopy));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookCopy> updateBookCopy(Long bookId, Long copyId, BookCopy bookCopyForm) {
        Optional<BookCopy> bookCopy = bookCopyRepository.findOneByIdInBook(bookId, copyId);
        if (bookCopy.isPresent()){
            bookCopy.get().setEditor(bookCopyForm.getEditor());
            return Optional.of(bookCopyRepository.save(bookCopy.get()));
        }
        return Optional.empty();
    }


    /**
     * Check if author exists, extract them as Set, and add association between book and author
     * @param book to check the authors from
     */
    private void manageAuthors(Book book) {
        Set<Author> listAuthor = new HashSet<>();
        for (Iterator<Author> it = book.getAuthors().iterator(); it.hasNext();) {
            Author author = it.next();
            Optional<Author> authorInDb = authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
            if(authorInDb.isPresent()){
                it.remove();
                listAuthor.add(authorInDb.get());
            }else{
                throw new UnknownAuthorException();
            }
        }
        book.setAuthors(listAuthor);
    }

    private boolean bookAlreadyExists(Book book) {
        List<Book> bookList = bookRepository.findByTitleLike(book.getTitle());
        if (!bookList.isEmpty()) {
            for (Book bookInList : bookList) {
                if(bookInList.getAuthors().equals(book.getAuthors()) && !bookInList.getId().equals(book.getId())){
                    return true;
                }
            }
        }
        return false;
    }

}
