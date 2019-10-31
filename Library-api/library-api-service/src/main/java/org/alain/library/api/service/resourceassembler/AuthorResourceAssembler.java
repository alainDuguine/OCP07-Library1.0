//package org.alain.library.api.service.resourceassembler;
//
//import org.alain.library.api.model.book.Author;
//import org.alain.library.api.webservice.restcontroller.AuthorRestService;
//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.ResourceAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
//
//@Component
//public class AuthorResourceAssembler implements ResourceAssembler<Author, Resource<Author>> {
//    @Override
//    public Resource<Author> toResource(Author author) {
//        return new Resource<>(author,
//                linkTo(methodOn(AuthorRestService.class).getAuthor(author.getId())).withSelfRel(),
//                linkTo(methodOn(AuthorRestService.class).getAuthors()).withRel("authors"));
//    }
//}
