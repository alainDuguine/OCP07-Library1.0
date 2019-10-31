//package org.alain.library.api.service.resourceassembler;
//
//import org.alain.library.api.model.loan.Loan;
//import org.alain.library.api.webservice.restcontroller.LoanRestService;
//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.ResourceAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
//
//@Component
//public class LoanResourceAssembler implements ResourceAssembler<Loan, Resource<Loan>>{
//    @Override
//    public Resource<Loan> toResource(Loan loan) {
//        return new Resource<>(loan,
//                linkTo(methodOn(LoanRestService.class).getLoan(loan.getId())).withSelfRel(),
//                linkTo(methodOn(LoanRestService.class).getLoans()).withRel("authors"));
//    }
//}