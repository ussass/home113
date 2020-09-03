package ru.trofimov.controllers;

import  org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.config.AppConfig;
import ru.trofimov.entity.Recipe;
import ru.trofimov.entity.Step;
import ru.trofimov.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @GetMapping("/main")
    public String showMain(@RequestHeader("User-Agent") String userAgent, Model model) {
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("pageName", "Рецепты");
        return DirtyJob.isMobile(userAgent) ? "recipe/recipesMobile" : "recipe/recipes";
    }

    @GetMapping("/list")
    public String showList(Model model,
                           @RequestHeader("User-Agent") String userAgent,
                           @RequestParam(defaultValue = "all") String category) {
        List<Recipe> list = WorkWithDB.findAll(Crutch.categoryStringToInt(category));
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("list", list);
        model.addAttribute("pageName", DirtyJob.intCategoryToString(Crutch.categoryStringToInt(category)));
        return DirtyJob.isMobile(userAgent) ? "recipe/listMobile" : "recipe/list";
    }

    @GetMapping("/{linkName}")
    public String showRecipe(@PathVariable(value = "linkName") String linkName, Model model) {
        try {
            int id = Integer.parseInt(linkName.split("-")[linkName.split("-").length - 1]);
            Recipe recipe = WorkWithDB.read(id);
            model.addAttribute("color", AppConfig.getColor());
            model.addAttribute("recipe", recipe);
            return "recipe/show";

        } catch (Exception e) {
            return "error404";
        }
    }

    @GetMapping("/add")
    public String showAdd(Model model) {
        model.addAttribute("color", AppConfig.getColor());
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
                          @RequestParam List<Integer> quantity,
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
                CreateClassesForRecipe.createIngredients(ingName, quantity, measure),
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
        model.addAttribute("color", AppConfig.getColor());
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
                           @RequestParam List<Integer> quantity,
                           @RequestParam int[] measure,
                           @RequestParam MultipartFile[] photoStep,
                           @RequestParam String[] step,
                           @RequestParam int id,
                           @RequestParam String oldPhotoNames,
                           @RequestParam(required = false) int[] delMainPhoto,
                           @RequestParam(required = false) int[] delStepPhoto,
                           @RequestParam(required = false) String[] oldStepPhotoNames
    ) throws UnsupportedEncodingException {
        WorkWithMultipartFile work = new WorkWithMultipartFile(photo, Crutch.toUTF8(recipeName));
        String[] firstMainImage = work.saveFiles(false);
        String[] secondMainImage = Crutch.removeImage(oldPhotoNames, photo, delMainPhoto, id, Crutch.toUTF8(recipeName), false, 0);
        String[] resultMainImage = Crutch.twoArraysIntoOne(firstMainImage, secondMainImage);
        work.setResultPhotoName(resultMainImage);

        String[] secondStepImage = Crutch.removStepeImage(oldStepPhotoNames, photoStep, delStepPhoto, id, Crutch.toUTF8(recipeName), true, step.length);
        WorkWithMultipartFile stepWork = new WorkWithMultipartFile(photoStep, Crutch.toUTF8(recipeName));
        String[] resultStepImage = Crutch.twoArraysIntoOne2(stepWork.saveFiles(true), secondStepImage);
        stepWork.setResultPhotoName(resultStepImage);

        Crutch.deleteAllFilesFolder(id);
        work.moveImg(id);
        stepWork.moveImg(id);
        Crutch.deleteAllFilesFolder(-1);

        System.out.println("step.length: " + step.length);
        System.out.println("resultStepImage.length: " + resultStepImage.length);


        Recipe recipe = new Recipe(
                Crutch.toUTF8(recipeName),
                category,
                listportion,
                listhour,
                listminut,
                resultMainImage,
                CreateClassesForRecipe.createIngredients(ingName, quantity , measure),
                CreateClassesForRecipe.createSteps(step, resultStepImage));


        WorkWithDB.update(id, recipe);



        return "redirect:/recipe/" + Crutch.toTranscript(recipe.getRecipeName()) + "-" + id;
    }

    @PostMapping("/del")
    public String delete(@RequestParam int id, Model model) {
        WorkWithDB.delete(id);
        Crutch.recursiveDelete(new File("D:/Java Project/NewProjects/home113/target/home113/upload/" + id));
        return "redirect:/recipe/main";
    }
}
