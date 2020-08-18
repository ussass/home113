package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.entity.Recipe;
import ru.trofimov.entity.Step;
import ru.trofimov.model.*;

import java.io.File;
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
    public String showList(Model model, @RequestParam(defaultValue = "all") String category) {
        List<Recipe> list = WorkWithDB.findAll(Crutch.categoryStringToInt(category));
        model.addAttribute("list", list);
        return "recipe/list";
    }

    @GetMapping("/{linkName}")
    public String showRecipe(@PathVariable(value = "linkName") String linkName, Model model) {
        try {
            int id = Integer.parseInt(linkName.split("-")[linkName.split("-").length - 1]);
            Recipe recipe = WorkWithDB.read(id);
            model.addAttribute("recipe", recipe);
            return "recipe/show";

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



        int id = WorkWithDB.save(recipe);
        work.moveImg(id);
        WorkWithMultipartFile workSteps = new WorkWithMultipartFile();
        workSteps.moveImg(id, recipe.getSteps());
        System.out.println(Crutch.toTranscript(recipe.getRecipeName()) + "-" + id);

        return "redirect:/recipe/" + Crutch.toTranscript(recipe.getRecipeName()) + "-" + id;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("recipe", WorkWithDB.read(id));
        return "recipe/edit";
    }

    @PostMapping("/edit")
    public String postEdit(Model model,
                           @RequestParam String recipeName,
                           @RequestParam int category,
                           @RequestParam int listportion,
                           @RequestParam int listhour,
                           @RequestParam int listminut,
                           @RequestParam MultipartFile[] photo,
                           @RequestParam String[] ingName,
                           @RequestParam List<Integer>  quantity,
                           @RequestParam int[] measure,
                           @RequestParam MultipartFile[] photoStep,
                           @RequestParam String[] step,
                           @RequestParam int id,
                           @RequestParam int[] delMainPhoto,
                           @RequestParam int[] delPhoto
    ) throws UnsupportedEncodingException {


        Recipe recipe = new Recipe(
                recipeName,
                category,
                listportion,
                listhour,
                listminut,
                new String[]{""},
                CreateClassesForRecipe.createIngredients(ingName, quantity , measure),
                new Step[]{new Step("")});



        System.out.println("recipeName: " + Crutch.toUTF8(recipeName));
        System.out.println("category: " + category);
        System.out.println("listportion: " + listportion);
        System.out.println("listhour: " + listhour);
        System.out.println("listminut: " + listminut);
        System.out.println("photo: " + photo[0].getResource().getFilename());
        System.out.println("delMainPhoto: " + delMainPhoto.length);
        System.out.println("delPhoto: " + delPhoto.length);


//        return "redirect:/recipe/main";// + Crutch.toTranscript(recipe.getRecipeName()) + "-" + id;
        return "redirect:/recipe/edit/" + id;
    }

    @PostMapping("/del")
    public String delete (@RequestParam int id, Model model) {
        WorkWithDB.delete(id);
        Crutch.recursiveDelete(new File("D:/Java Project/NewProjects/home113/target/home113/upload/" + id));
        return "redirect:/recipe/main";
    }
}
