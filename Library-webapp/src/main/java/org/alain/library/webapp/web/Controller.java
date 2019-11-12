package org.alain.library.webapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    @GetMapping("/home")
    public String index(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/search")
    public String home(){
        return "search";
    }
}
