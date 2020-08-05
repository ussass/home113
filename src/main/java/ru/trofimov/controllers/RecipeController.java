package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.entity.Recipe;
import ru.trofimov.model.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @GetMapping("/main")
    public String showMain(){
        return "recipe/recipes";
    }

    @GetMapping("/list")
    public String showList(){
        return "recipe/list";
    }

    @GetMapping("/add")
    public String showAdd(){
        return "recipe/add";
    }

    @PostMapping("/add")
    public String postAdd( Model model,
            @RequestParam String recipeName,
            @RequestParam int category,
            @RequestParam int listportion,
            @RequestParam int listhour,
            @RequestParam int listminut,
            @RequestParam MultipartFile[] photo,
            @RequestParam String[] ingName,
            @RequestParam int[] quantity,
            @RequestParam int[] measure
            ) throws UnsupportedEncodingException {

        Recipe recipe = new Recipe(Crutch.toUTF8(recipeName), category, listportion, listhour, listminut);

        WorkWithMultipartFile work = new WorkWithMultipartFile(photo, Crutch.toUTF8(recipeName));
        recipe.setNamesMainImage(work.saveFiles(false));
        recipe.setIngredients(CreateClassesForRecipe.createIngredients(ingName, quantity, measure));


        recipe.showFields();


        return "redirect:/recipe/add";
    }
}
