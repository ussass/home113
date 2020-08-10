package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.entity.Recipe;
import ru.trofimov.model.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @GetMapping("/main")
    public String showMain() {
        return "recipe/recipes";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<Recipe> list = WorkWithDB.findAll(0);
        model.addAttribute("list", list);
        return "recipe/list";
    }

//    @GetMapping("/main")
    @GetMapping("/{linkName}")
    public String showRecipe(@PathVariable(value = "linkName") String linkName, Model model) {
        try {
            int id = Integer.parseInt(linkName.split("-")[linkName.split("-").length - 1]);

            return "recipe/list";

        }catch (Exception e){
            return "error404";
        }

    }

    @GetMapping("/add")
    public String showAdd() {
        return "recipe/add";
    }

    @PostMapping("/add")
    public String postAdd(Model model,
                          @RequestParam String recipeName,
                          @RequestParam int category,
                          @RequestParam int listportion,
                          @RequestParam int listhour,
                          @RequestParam int listminut,
                          @RequestParam MultipartFile[] photo,
                          @RequestParam String[] ingName,
//                          @RequestParam (value = "quantity", required = false) int[] quantity,
                          @RequestParam List<Integer>  quantity,
                          @RequestParam int[] measure,
                          @RequestParam MultipartFile[] photoStep,
                          @RequestParam String[] step
    ) throws UnsupportedEncodingException {


        WorkWithMultipartFile work = new WorkWithMultipartFile(photo, Crutch.toUTF8(recipeName));
        Recipe recipe = new Recipe(
                Crutch.toUTF8(recipeName),
                category,
                listportion,
                listhour,
                listminut,
                work.saveFiles(false),
                CreateClassesForRecipe.createIngredients(ingName, quantity , measure),
                CreateClassesForRecipe.createSteps(photoStep, step, Crutch.toUTF8(recipeName)));


        recipe.showFields();
        System.out.println("-----------------");
        System.out.println(recipe.insertIntoDb());

        int id = WorkWithDB.save(recipe);
        System.out.println(id);
        work.moveImg(id);

        return "redirect:/recipe/add";
    }
}
