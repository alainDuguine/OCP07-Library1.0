package org.alain.library.webapp.web;

import io.swagger.client.api.AuthorApi;
import io.swagger.client.api.BookApi;
import io.swagger.client.api.LoanApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.BookDto;
import io.swagger.client.model.LoanDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    private final LoanApi loanApi;
    private final AuthorApi authorApi;
    private final BookApi bookApi;
    private final UserApi userApi;

    public Controller(LoanApi loanApi, AuthorApi authorApi, BookApi bookApi, UserApi userApi) {
        this.loanApi = loanApi;
        this.authorApi = authorApi;
        this.bookApi = bookApi;
        this.userApi = userApi;
    }


    @GetMapping("/")
    public String def(){return "redirect:/login";}

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/loans")
    public String loans(Model model){
        try {
            List<LoanDto> loanDtoList = loanApi.getLoans(null,1L).execute().body();
            model.addAttribute("loanList", loanDtoList);
        }catch (Exception ex){
            return "Connexion failed";
        }
        return "loans";
    }

    @GetMapping({"/search", "/books"})
    public String books(Model model,
                        @RequestParam(name = "title", defaultValue = "") String title,
                        @RequestParam(name = "author", defaultValue = "") String author){
        try {
            List<BookDto> bookDtoLists = bookApi.getBooks(title, author).execute().body();
            model.addAttribute("books", bookDtoLists);
        }catch (Exception ex){
            return "Connexion failed";
        }
        return "search";
    }
}
