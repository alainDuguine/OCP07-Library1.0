//package org.alain.library.api.service.resourceassembler;
//
//import org.alain.library.api.model.user.User;
//import org.alain.library.api.webservice.restcontroller.UserRestService;
//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.ResourceAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
//
//
//@Component
//public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {
//
//    @Override
//    public Resource<User> toResource(User user) {
//        return new Resource<>(user,
//                linkTo(methodOn(UserRestService.class).getUser(user.getId())).withSelfRel(),
//                linkTo(methodOn(UserRestService.class).getUsers()).withRel("users"));
//    }
//}
