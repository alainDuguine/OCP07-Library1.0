package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.BookManagement;
import org.alain.library.api.consumer.repository.AuthorRepository;
import org.alain.library.api.consumer.repository.BookRepository;
import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookManagementImpl extends CrudManagerImpl<Book> implements BookManagement {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookManagementImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleLike("%"+title+"%");
    }

//    @Override
//    public boolean checkIfBookExists(List<Book> bookList, BookForm bookForm) {
//        // Comment comparer les deux listes SET pour ne pas tenir compte de l'ordre ?
//        Set<String> authorsInBook = new HashSet<>();
//        Set<String> authorsInForm = new HashSet<>();
//        for(Book bookInList : bookList){
//            for (Author author : bookInList.getAuthors()){
//                String authorName = author.getFirstName() + ' ' + author.getLastName();
//                authorsInBook.add(authorName);
//            }
//        }
//        for (BooksAuthors author : bookForm.getAuthors()){
//            String authorName = author.getFirstName() + ' ' + author.getLastName();
//            authorsInForm.add(authorName);
//        }
//        return authorsInBook.equals(authorsInForm);
//    }

    @Override
    public Book saveNewBook(Book book, Set<Author> authors) {
        // Check if author already exists, otherwise we save it
        for(Author author: authors){
            Optional<Author> authorInDb = authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
            if(authorInDb.isPresent()){
                author = authorInDb.get();
            }
            author.addBook(book);
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book, Set<Author> authors) {
//        OBook book2 = bookRepository.findById(id);
        if(!compareListAuthorsByName(book.getAuthors(),authors)){
            for(Author author : book.getAuthors()){
                author.removeBook(book);
            }
        }
        return this.saveNewBook(book, authors);

    }

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

    private boolean compareListAuthorsByName(Set<Author> authors1, Set<Author> authors2){
        Set<String> authorNameList1 = new HashSet<>();
        Set<String> authorNameList2 = new HashSet<>();
        for (Author author : authors1){
            String authorName = author.getFirstName() + ' ' + author.getLastName();
            authorNameList1.add(authorName);
        }
        for (Author author : authors2){
            String authorName = author.getFirstName() + ' ' + author.getLastName();
            authorNameList2.add(authorName);
        }
        return authorNameList1.equals(authorNameList2);
    }

}
