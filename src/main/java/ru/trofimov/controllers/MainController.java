package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showIndex(){
        return "index";
    }

    @GetMapping("error404")
    public String show404(){
        return "error404";
    }

}
