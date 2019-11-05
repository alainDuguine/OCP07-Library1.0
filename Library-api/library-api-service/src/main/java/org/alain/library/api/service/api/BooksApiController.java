package org.alain.library.api.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.alain.library.api.business.contract.BookManagement;
import org.alain.library.api.business.exceptions.UnknownAuthorException;
import org.alain.library.api.business.exceptions.UnknownBookException;
import org.alain.library.api.model.book.Author;
import org.alain.library.api.model.book.Book;
import org.alain.library.api.model.book.BookCopy;
import org.alain.library.api.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-01T07:19:44.790+01:00")

@Controller
public class BooksApiController implements BooksApi {

    private static final Logger log = LoggerFactory.getLogger(BooksApiController.class);

    private final ObjectMapper objectMapper;
    private final BookManagement bookManagement;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public BooksApiController(ObjectMapper objectMapper, BookManagement bookManagement, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.bookManagement = bookManagement;
        this.request = request;
    }

    public ResponseEntity<BookDto> getBook(@ApiParam(value = "Id of book to return",required=true) @PathVariable("id") Long id) {
        Optional<Book> book = bookManagement.findOne(id);
        if (book.isPresent()){
            return new ResponseEntity<BookDto>(convertBookModelToBookDto(book.get()),HttpStatus.OK);
        }
        return new ResponseEntity<BookDto>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<BookDto>> getBooks(@ApiParam(value = "Title of book to return", defaultValue = "") @Valid @RequestParam(value = "title", required = false, defaultValue="") String title) {
        List<Book> bookList = bookManagement.findByTitle(title);
        return new ResponseEntity<List<BookDto>>(convertListBookModelToListBookDto(bookList), HttpStatus.OK);
    }

    public ResponseEntity<BookCopyDto> getCopy(@ApiParam(value = "Book id concerned by the copy",required=true) @PathVariable("bookId") Long bookId,
                                               @ApiParam(value = "BookCopy id to get",required=true) @PathVariable("copyId") Long copyId) {
        Optional<BookCopy> bookCopy = bookManagement.findCopyInBook(bookId, copyId);
        if (bookCopy.isPresent()){
            return new ResponseEntity<BookCopyDto>(convertBookCopyModelToBookCopyDto(bookCopy.get()),HttpStatus.OK);
        }
        return new ResponseEntity<BookCopyDto>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<BookCopyDto>> getCopies(@ApiParam(value = "Id of book to find copies from",required=true) @PathVariable("id") Long id) {
        try {
            List<BookCopy> bookCopyList = bookManagement.findCopiesInBook(id);
            return new ResponseEntity<List<BookCopyDto>>(convertListBookCopyModelToListBookCopyDto(bookCopyList), HttpStatus.OK);
        }catch (UnknownBookException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    public ResponseEntity<Void> deleteBook(@ApiParam(value = "Book id to delete",required=true) @PathVariable("id") Long id) {
        bookManagement.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> deleteBookCopy(@ApiParam(value = "Book id to delete a copy from",required=true) @PathVariable("bookId") Long bookId,
                                               @ApiParam(value = "BookCopy id to delete",required=true) @PathVariable("copyId") Long copyId) {
        bookManagement.deleteCopyInBook(bookId, copyId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<BookDto> addBook(@ApiParam(value = "Book object that needs to be added to the database" ,required=true ) @Valid @RequestBody BookForm bookForm) {
        try {
            Optional<Book> bookModel = bookManagement.saveBook(convertBookFormToBookModel(bookForm));
            if(bookModel.isPresent()){
                return new ResponseEntity<BookDto>(convertBookModelToBookDto(bookModel.get()), HttpStatus.OK);
            }
            return new ResponseEntity<BookDto>(HttpStatus.CONFLICT);
        }catch(UnknownAuthorException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    public ResponseEntity<BookCopyDto> addBookCopy(@ApiParam(value = "Id of book to find copies from",required=true) @PathVariable("id") Long id,
                                                   @ApiParam(value = "Book copy object that needs to be added to the database" ,required=true )  @Valid @RequestBody CopyForm copyForm) {
        Optional<BookCopy> bookCopyModel = bookManagement.saveBookCopy(id, convertBookCopyFormToBookCopyModel(copyForm));
        if(bookCopyModel.isPresent()){
            return new ResponseEntity<BookCopyDto>(convertBookCopyModelToBookCopyDto(bookCopyModel.get()), HttpStatus.OK);
        }
        return new ResponseEntity<BookCopyDto>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<BookDto> updateBook(@ApiParam(value = "Book id to update",required=true) @PathVariable("id") Long id,
                                              @ApiParam(value = "Book object updated" ,required=true )  @Valid @RequestBody BookDto book) {
        try {
            Optional<Book> bookModel = bookManagement.updateBook(id, convertBookDtoToBookModel(book));
            if (bookModel.isPresent()) {
                return new ResponseEntity<BookDto>(convertBookModelToBookDto(bookModel.get()), HttpStatus.OK);
            }
            return new ResponseEntity<BookDto>(HttpStatus.CONFLICT);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    public ResponseEntity<BookCopyDto> updateBookCopy(@ApiParam(value = "Book id to update copy from",required=true) @PathVariable("bookId") Long bookId,
                                                      @ApiParam(value = "BookCopy id to update",required=true) @PathVariable("copyId") Long copyId,
                                                      @ApiParam(value = "Book copy object updated" ,required=true )  @Valid @RequestBody BookCopyDto copy) {
        Optional<BookCopy> bookCopyModel = bookManagement.updateBookCopy(bookId, copyId, convertBookCopyDtoToBookCopyModel(copy));
        if (bookCopyModel.isPresent()) {
            return new ResponseEntity<BookCopyDto>(convertBookCopyModelToBookCopyDto(bookCopyModel.get()), HttpStatus.OK);
        }
        return new ResponseEntity<BookCopyDto>(HttpStatus.NOT_FOUND);
    }


    //=======================================================================
    //================== CONVERTERS =========================================
    //=======================================================================


    //================== Book =========================================

    private BookDto convertBookModelToBookDto(Book bookModel) {
        BookDto bookDto = new BookDto();
        bookDto.setId(bookModel.getId());
        bookDto.setIsbn(bookModel.getIsbn());
        bookDto.setTitle(bookModel.getTitle());
        for (Author author : bookModel.getAuthors()){
            bookDto.addAuthorsItem(convertAuthorModelToBooksAuthors(author));
        }
        return bookDto;
    }

    private Book convertBookDtoToBookModel(BookDto bookDto) {
        Book bookModel = new Book(bookDto.getTitle());
        try {
            for (BooksAuthors booksAuthors : bookDto.getAuthors()) {
                bookModel.addAuthor(convertBooksAuthorsToAuthorModel(booksAuthors));
            }
            return bookModel;
        }catch (NullPointerException ex){
            throw new UnknownAuthorException("A book should have at least one author");
        }
    }

    private List<BookDto> convertListBookModelToListBookDto(List<Book> bookList) {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book bookModel : bookList) {
            bookDtoList.add(convertBookModelToBookDto(bookModel));
        }
        return bookDtoList;
    }

    private Book convertBookFormToBookModel(BookForm bookForm) {
        Book bookModel = new Book(bookForm.getTitle());
        try {
            for (BooksAuthors booksAuthors : bookForm.getAuthors()) {
                bookModel.addAuthor(convertBooksAuthorsToAuthorModel(booksAuthors));
            }
            return bookModel;
        }catch (NullPointerException ex){
            throw new UnknownAuthorException("A book should have at least one author");
        }
    }


    //================== Author =========================================

    private BooksAuthors convertAuthorModelToBooksAuthors(Author authorModel){
        BooksAuthors booksAuthors = new BooksAuthors();
        booksAuthors.setFirstName(authorModel.getFirstName());
        booksAuthors.setLastName(authorModel.getLastName());
        return booksAuthors;
    }

    private Author convertBooksAuthorsToAuthorModel(BooksAuthors booksAuthors) {
        Author author = new Author();
        author.setFirstName(booksAuthors.getFirstName());
        author.setLastName(booksAuthors.getLastName());
        return author;
    }


    //================== BookCopy =========================================

    private BookCopyDto convertBookCopyModelToBookCopyDto(BookCopy bookCopy) {
        BookCopyDto bookCopyDto = new BookCopyDto();
        bookCopyDto.setId(bookCopy.getId());
        bookCopyDto.setBarcode(bookCopy.getBarcode());
        bookCopyDto.setEditor(bookCopy.getEditor());
        bookCopyDto.setAvailable(bookCopy.isAvailable());
        bookCopyDto.setBook(convertBookModelToBookDto(bookCopy.getBook()));
        return bookCopyDto;
    }

    private BookCopy convertBookCopyDtoToBookCopyModel(BookCopyDto bookCopyDto) {
        BookCopy bookCopyModel = new BookCopy();
        bookCopyModel.setEditor(bookCopyDto.getEditor());
        return bookCopyModel;
    }

    private List<BookCopyDto> convertListBookCopyModelToListBookCopyDto(List<BookCopy> bookCopyList) {
        List<BookCopyDto> bookCopyDtoList = new ArrayList<>();
        for (BookCopy bookCopy : bookCopyList){
            bookCopyDtoList.add(convertBookCopyModelToBookCopyDto(bookCopy));
        }
        return bookCopyDtoList;
    }

    private BookCopy convertBookCopyFormToBookCopyModel(CopyForm bookCopyForm) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setEditor(bookCopyForm.getEditor());
        if(bookCopyForm.getBarcode() != null){
            bookCopy.setBarcode(bookCopyForm.getBarcode());
        }
        return bookCopy;
    }

}
