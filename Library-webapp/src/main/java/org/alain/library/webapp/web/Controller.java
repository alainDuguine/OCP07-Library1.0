package org.alain.library.webapp.web;

import io.swagger.client.api.AuthorApi;
import io.swagger.client.api.BookApi;
import io.swagger.client.api.LoanApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.BookDto;
import io.swagger.client.model.LoanDto;
import io.swagger.client.model.UserCredentials;
import io.swagger.client.model.UserDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@org.springframework.stereotype.Controller
@RequestMapping("/")
@SessionAttributes("UserSession")
public class Controller {

    private final LoanApi loanApi;
    private final BookApi bookApi;
    private final UserApi userApi;

    public Controller(LoanApi loanApi, BookApi bookApi, UserApi userApi) {
        this.loanApi = loanApi;
        this.bookApi = bookApi;
        this.userApi = userApi;
    }


    @GetMapping("/")
    public String def(){return "redirect:/login";}

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/signin")
    public String signin(HttpServletRequest request,
                        @RequestParam(name = "username")String username,
                         @RequestParam(name = "password")String password){
        try{
            UserCredentials userCredentials = new UserCredentials();
            userCredentials.setEmail(username);
            userCredentials.setPassword(password);
            if(userApi.login(userCredentials).execute().code() == 200){
                return "redirect:/home";
            }
        }catch (Exception ex){
            return "Connexion failed";
        }
        return "login";
    }

    @GetMapping("/loans")
    public String loans(Model model){
        try {
            UserDto user = userApi.getUserByEmail("alain_duguine@hotmail.fr").execute().body();
            String authorization = "Basic " + Base64.getEncoder().encodeToString(("alain_duguine@hotmail.fr:admin").getBytes());
//            List<LoanDto> loanDtoList = user.get;
//            model.addAttribute("loanList", loanDtoList);
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
