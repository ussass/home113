package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.entity.Recipe;
import ru.trofimov.model.Crutch;
import ru.trofimov.model.DirtyJob;
import ru.trofimov.model.WorkWithMultipartFile;

import java.io.IOException;
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
    public String postAdd(
            Model model,
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
//        System.out.println("I'm here!");
//        System.out.println("RecipeName: " + recipeName);
//        System.out.println("Category: " + category);
//        System.out.println("Listportion: " + listportion);
//        System.out.println("Time: " + listhour + ":" + listminut);
//        System.out.println(photo[0].getResource().getFilename());
//        System.out.println("ingredients:");
//        for (String x : ingName)
//            System.out.println("   " + x);

        Recipe recipe = new Recipe(Crutch.toUTF8(recipeName), category, listportion, listhour, listminut);

        System.out.println(DirtyJob.multipartToString(photo).toString());

//        WorkWithMultipartFile.getFileNameOfMultipartFile(photo[0], recipeName);
        WorkWithMultipartFile work = new WorkWithMultipartFile(photo, Crutch.toUTF8(recipeName));
        recipe.setNamesMainImage(work.saveFiles());


        recipe.showFields();


        return "redirect:/recipe/add";
    }
}
