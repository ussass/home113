package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.config.AppConfig;
import ru.trofimov.model.*;
import ru.trofimov.service.RecipeService;
import ru.trofimov.service.RecipeServiceImp;
import ru.trofimov.utils.CreateClassesForRecipe;
import ru.trofimov.utils.Crutch;
import ru.trofimov.utils.Utils;
import ru.trofimov.utils.WorkWithMultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @GetMapping("/main")
    public String showMain(@RequestHeader("User-Agent") String userAgent, Model model) {
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("pageName", "Рецепты");
        return Utils.isMobile(userAgent) ? "recipe/recipesMobile" : "recipe/recipes";
    }

    @GetMapping("/list")
    public String showList(Model model,
                           @RequestHeader("User-Agent") String userAgent,
                           @RequestParam(defaultValue = "all") String category) {
        RecipeService service = new RecipeServiceImp();
        List<Recipe> list1 = service.findAll(Crutch.categoryStringToInt(category));
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("list", list1);
        model.addAttribute("pageName", Utils.intCategoryToString(Crutch.categoryStringToInt(category)));
        return Utils.isMobile(userAgent) ? "recipe/listMobile" : "recipe/list";
    }

    @GetMapping("/{linkName}")
    public String showRecipe(Model model,
                             @PathVariable(value = "linkName") String linkName,
                             @RequestHeader("User-Agent") String userAgent) {
        try {
            int id = Integer.parseInt(linkName.split("-")[linkName.split("-").length - 1]);
            RecipeService service = new RecipeServiceImp();
            Recipe recipe = service.findById(id);
            recipe.initializationOfDependentClasses();
            model.addAttribute("color", AppConfig.getColor());
            model.addAttribute("recipe", recipe);
            model.addAttribute("pageName", recipe.getCategoryString());
            return Utils.isMobile(userAgent) ? "recipe/showMobile" : "recipe/show";

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return "error404";
        }
    }

    @GetMapping("/add")
    public String showAdd(Model model, @RequestHeader("User-Agent") String userAgent) {
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("pageName", "Добавить рецепт");
        return Utils.isMobile(userAgent) ? "recipe/addMobile" : "recipe/add";
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
                work.saveFilesString(false),
                CreateClassesForRecipe.createIngredientsString(ingName, quantity, measure),
                CreateClassesForRecipe.createStepsString(photoStep, step, Crutch.toUTF8(recipeName)));


        RecipeService service = new RecipeServiceImp();
        service.save(recipe);


        int id = recipe.getId();
        work.moveImg(id);
        WorkWithMultipartFile workSteps = new WorkWithMultipartFile();
        workSteps.moveImg(id, recipe.getStepsString());

        return "redirect:/recipe/" + Crutch.toTranscript(recipe.getRecipeName()) + "-" + id;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(Model model,
                           @RequestHeader("User-Agent") String userAgent,
                           @PathVariable(value = "id") int id) {
        RecipeService service = new RecipeServiceImp();
        Recipe recipe = service.findById(id);
        recipe.initializationOfDependentClasses();
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("recipe", recipe);

        return Utils.isMobile(userAgent) ? "recipe/editMobile" : "recipe/edit";
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

        Recipe recipe = new Recipe(
                Crutch.toUTF8(recipeName),
                category,
                listportion,
                listhour,
                listminut,
                Utils.ArrayStringToString(resultMainImage),
                CreateClassesForRecipe.createIngredientsString(ingName, quantity , measure),
                CreateClassesForRecipe.createStepsString(step, resultStepImage));
        recipe.setId(id);

        RecipeService service = new RecipeServiceImp();
        service.update(recipe);

        return "redirect:/recipe/" + Crutch.toTranscript(recipe.getRecipeName()) + "-" + id;
    }

    @PostMapping("/del")
    public String delete(@RequestParam int id, Model model) {
        RecipeService service = new RecipeServiceImp();
        Recipe recipe = new Recipe();
        recipe.setId(id);
        service.delete(recipe);
        Crutch.recursiveDelete(new File("D:/Java Project/NewProjects/home113/target/home113/upload/" + id));
        return "redirect:/recipe/main";
    }
}
