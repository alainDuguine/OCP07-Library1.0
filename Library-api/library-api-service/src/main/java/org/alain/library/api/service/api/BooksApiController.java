package org.alain.library.api.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.alain.library.api.service.dto.BookCopyDto;
import org.alain.library.api.service.dto.BookDto;
import org.alain.library.api.service.dto.BookForm;
import org.alain.library.api.service.dto.CopyForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-31T15:23:24.407+01:00")

@Controller
public class BooksApiController implements BooksApi {

    private static final Logger log = LoggerFactory.getLogger(BooksApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public BooksApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<BookDto> addBook(@ApiParam(value = "Book object that needs to be added to the database" ,required=true )  @Valid @RequestBody BookForm bookForm) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<BookDto>(objectMapper.readValue("{  \"isbn\" : \"isbn\",  \"id\" : 0,  \"title\" : \"title\",  \"authors\" : [ {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  }, {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  } ]}", BookDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<BookDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BookDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<BookCopyDto> addBookCopy(@ApiParam(value = "Id of book to find copies from",required=true) @PathVariable("id") Long id,@ApiParam(value = "Book copy object that needs to be added to the database" ,required=true )  @Valid @RequestBody CopyForm copyForm) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<BookCopyDto>(objectMapper.readValue("{  \"editor\" : \"editor\",  \"book\" : \"{}\",  \"id\" : 0,  \"barcode\" : \"barcode\"}", BookCopyDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<BookCopyDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BookCopyDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteBook(@ApiParam(value = "Book id to delete",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteBookCopy(@ApiParam(value = "Book id to delete a copy from",required=true) @PathVariable("bookId") Long bookId,@ApiParam(value = "BookCopy id to delete",required=true) @PathVariable("copyId") Long copyId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<BookDto> getBook(@ApiParam(value = "Id of book to return",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<BookDto>(objectMapper.readValue("{  \"isbn\" : \"isbn\",  \"id\" : 0,  \"title\" : \"title\",  \"authors\" : [ {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  }, {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  } ]}", BookDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<BookDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BookDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<BookDto>> getBooks(@ApiParam(value = "Title of book to return", defaultValue = "") @Valid @RequestParam(value = "title", required = false, defaultValue="") String title) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BookDto>>(objectMapper.readValue("[ {  \"isbn\" : \"isbn\",  \"id\" : 0,  \"title\" : \"title\",  \"authors\" : [ {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  }, {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  } ]}, {  \"isbn\" : \"isbn\",  \"id\" : 0,  \"title\" : \"title\",  \"authors\" : [ {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  }, {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  } ]} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BookDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BookDto>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<BookCopyDto>> getCopies(@ApiParam(value = "Id of book to find copies from",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BookCopyDto>>(objectMapper.readValue("[ {  \"editor\" : \"editor\",  \"book\" : \"{}\",  \"id\" : 0,  \"barcode\" : \"barcode\"}, {  \"editor\" : \"editor\",  \"book\" : \"{}\",  \"id\" : 0,  \"barcode\" : \"barcode\"} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BookCopyDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BookCopyDto>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<BookDto> updateBook(@ApiParam(value = "Book id to update",required=true) @PathVariable("id") Long id,@ApiParam(value = "Book object updated" ,required=true )  @Valid @RequestBody BookDto book) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<BookDto>(objectMapper.readValue("{  \"isbn\" : \"isbn\",  \"id\" : 0,  \"title\" : \"title\",  \"authors\" : [ {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  }, {    \"firstName\" : \"firstName\",    \"lastName\" : \"lastName\"  } ]}", BookDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<BookDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BookDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<BookCopyDto> updateBookCopy(@ApiParam(value = "Book id to update copy from",required=true) @PathVariable("bookId") Long bookId,@ApiParam(value = "BookCopy id to update",required=true) @PathVariable("copyId") Long copyId,@ApiParam(value = "Book copy object updated" ,required=true )  @Valid @RequestBody BookCopyDto copy) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<BookCopyDto>(objectMapper.readValue("{  \"editor\" : \"editor\",  \"book\" : \"{}\",  \"id\" : 0,  \"barcode\" : \"barcode\"}", BookCopyDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<BookCopyDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<BookCopyDto>(HttpStatus.NOT_IMPLEMENTED);
    }

}
