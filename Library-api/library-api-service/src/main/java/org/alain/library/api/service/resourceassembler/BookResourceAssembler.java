//package org.alain.library.api.service.resourceassembler;
//
//import org.alain.library.api.model.book.Book;
//import org.alain.library.api.webservice.restcontroller.BookRestService;
//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.ResourceAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
//
//@Component
//public class BookResourceAssembler implements ResourceAssembler<Book, Resource<Book>>{
//    @Override
//    public Resource<Book> toResource(Book book) {
//        return new Resource<>(book,
//                linkTo(methodOn(BookRestService.class).getBook(book.getId())).withSelfRel(),
//                linkTo(methodOn(BookRestService.class).getBooks()).withRel("books"));
//    }
//}
