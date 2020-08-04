package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @GetMapping("/main")
    public String showMain(){
        return "recipe/recipes";
    }
}
