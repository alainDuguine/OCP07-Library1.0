package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.AuthorManagement;
import org.alain.library.api.business.contract.BookManagement;
import org.alain.library.api.business.exceptions.UnknownAuthorException;
import org.alain.library.api.consumer.repository.BookCopyRepository;
import org.alain.library.api.consumer.repository.BookRepository;
import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;
import org.alain.library.api.model.book.BookCopy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookManagementImpl extends CrudManagerImpl<Book> implements BookManagement {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final AuthorManagement authorManagement;

    public BookManagementImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository, AuthorManagement authorManagement) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.authorManagement = authorManagement;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleLike("%"+title+"%");
    }

    @Override
    public Optional<BookCopy> findCopyInBook(Long bookId, Long copyId) {
        return bookCopyRepository.findOneByIdInBook(bookId, copyId);
    }

    @Override
    public List<BookCopy> findCopiesInBook(Long id) {
        return bookCopyRepository.findAllByBookId(id);
    }

    @Override
    public void deleteCopyInBook(Long bookId, Long copyId) {
        bookCopyRepository.deleteOneByIdInBook(bookId, copyId);
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

    /**
     * Check if author exists, extract them as Set, and add association between book and author
     * @param book to check the authors from
     */
    private void manageAuthors(Book book) {
        Set<Author> listAuthor = new HashSet<>();
        for (Iterator<Author> it = book.getAuthors().iterator(); it.hasNext();) {
            Author author = it.next();
            Optional<Author> authorInDb = authorManagement.findAuthorByFullName(author.getFirstName(), author.getLastName());
            if(authorInDb.isPresent()){
                it.remove();
                listAuthor.add(authorInDb.get());
            }else{
                throw new UnknownAuthorException();
            }
        }
        book.setAuthors(listAuthor);
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

    private boolean bookAlreadyExists(Book book) {
        List<Book> bookList = bookRepository.findByTitleLike(book.getTitle());
        if (!bookList.isEmpty()) {
            for (Book bookInList : bookList) {
                if(bookInList.getAuthors().equals(book.getAuthors())){
                    return true;
                }
            }
        }
        return false;
    }

//    private boolean compareListAuthorsByName(Set<Author> authors1, Set<Author> authors2){
//        Set<String> authorNameList1 = new HashSet<>();
//        Set<String> authorNameList2 = new HashSet<>();
//        for (Author author : authors1){
//            String authorName = author.getFirstName() + ' ' + author.getLastName();
//            authorNameList1.add(authorName);
//        }
//        for (Author author : authors2){
//            String authorName = author.getFirstName() + ' ' + author.getLastName();
//            authorNameList2.add(authorName);
//        }
//        return authorNameList1.equals(authorNameList2);
//    }


//    @Override
//    public Book saveNewBook(Book book, Set<Author> authors) {
//        // Check if author already exists, otherwise we save it
//        for(Author author: authors){
//            Optional<Author> authorInDb = authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
//            if(authorInDb.isPresent()){
//                author = authorInDb.get();
//            }
//            author.addBook(book);
//        }
//        return bookRepository.save(book);
//    }
//
//    @Override
//    public Book updateBook(Long id, Book book, Set<Author> authors) {
////        OBook book2 = bookRepository.findById(id);
//        if(!compareListAuthorsByName(book.getAuthors(),authors)){
//            for(Author author : book.getAuthors()){
//                author.removeBook(book);
//            }
//        }
//        return this.saveNewBook(book, authors);
//
//    }

//    @Override
//    public Book updateBook(Long id, BookDto bookDto, Set<Author> authors) {
//        Book book = bookRepository.findById(id).get();
//        book.setTitle(bookDto.getTitle());
//        if(bookDto.getIsbn() != null) {
//            book.setIsbn(bookDto.getIsbn());
//        }
//        if(!compareListAuthorsByName(book.getAuthors(), authors)){
//            Iterator iterator = book.getAuthors().iterator();
//            while (iterator.hasNext()) {
//                book.removeAuthor((Author) iterator.next());
//            }
//        }
//        return this.saveNewBook(book, authors);
//    }



}
