package org.alain.library.webapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    @GetMapping("/")
    public String def(){return "redirect:/login";}

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String index(){
        return "home";
    }

    @GetMapping("/search")
    public String home(){
        return "search";
    }
}
