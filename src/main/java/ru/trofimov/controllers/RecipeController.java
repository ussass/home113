package ru.trofimov.controllers;

import  org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.config.AppConfig;
import ru.trofimov.entity.Recipe;
import ru.trofimov.model.*;
import ru.trofimov.service.RecipeService;
import ru.trofimov.service.RecipeServiceImp;
import ru.trofimov.utils.HibernateSessionFactoryUtil;

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
        return DirtyJob.isMobile(userAgent) ? "recipe/recipesMobile" : "recipe/recipes";
    }

    @GetMapping("/list")
    public String showList(Model model,
                           @RequestHeader("User-Agent") String userAgent,
                           @RequestParam(defaultValue = "all") String category) {
//        List<Recipe> list = WorkWithDB.findAll(Crutch.categoryStringToInt(category));
        RecipeService service = new RecipeServiceImp();
        List<ru.trofimov.model.Recipe> list1 = service.findAll(Crutch.categoryStringToInt(category));
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("list", list1);
        model.addAttribute("pageName", DirtyJob.intCategoryToString(Crutch.categoryStringToInt(category)));
        return DirtyJob.isMobile(userAgent) ? "recipe/listMobile" : "recipe/list";
    }

    @GetMapping("/{linkName}")
    public String showRecipe(Model model,
                             @PathVariable(value = "linkName") String linkName,
                             @RequestHeader("User-Agent") String userAgent) {
        try {
            int id = Integer.parseInt(linkName.split("-")[linkName.split("-").length - 1]);
//            Recipe recipe = WorkWithDB.read(id);
            RecipeService service = new RecipeServiceImp();
            ru.trofimov.model.Recipe recipe1 = service.findById(id);
            recipe1.initializationOfDependentClasses();
//            System.out.println("recipe1.toString() = " + recipe1.toString());
            System.out.println(11);
            System.out.println("recipe1.arrayLength() = " + recipe1.arrayLength());
            model.addAttribute("color", AppConfig.getColor());
            model.addAttribute("recipe", recipe1);
            model.addAttribute("pageName", recipe1.getCategoryString());
            return DirtyJob.isMobile(userAgent) ? "recipe/showMobile" : "recipe/show";

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return "error404";
        }
    }

    @GetMapping("/add")
    public String showAdd(Model model, @RequestHeader("User-Agent") String userAgent) {
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("pageName", "Добавить рецепт");
        return DirtyJob.isMobile(userAgent) ? "recipe/addMobile" : "recipe/add";
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

        ru.trofimov.model.Recipe recipe1 = new ru.trofimov.model.Recipe(
                Crutch.toUTF8(recipeName),
                category,
                listportion,
                listhour,
                listminut,
                work.saveFilesString(false),
                CreateClassesForRecipe.createIngredientsString(ingName, quantity, measure),
                CreateClassesForRecipe.createStepsString(photoStep, step, Crutch.toUTF8(recipeName)));


        RecipeService service = new RecipeServiceImp();
        service.save(recipe1);
        System.out.println(recipe1.toString());


        int id = recipe1.getId();
        work.moveImg(id);
        WorkWithMultipartFile workSteps = new WorkWithMultipartFile();
        System.out.println("recipe1.getStepsString() = " + recipe1.getStepsString());
        workSteps.moveImg(id, recipe1.getStepsString());
        System.out.println(Crutch.toTranscript(recipe1.getRecipeName()) + "-" + id);

        return "redirect:/recipe/" + Crutch.toTranscript(recipe1.getRecipeName()) + "-" + id;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(Model model,
                           @RequestHeader("User-Agent") String userAgent,
                           @PathVariable(value = "id") int id) {
        RecipeService service = new RecipeServiceImp();
        ru.trofimov.model.Recipe recipe = service.findById(id);
        recipe.initializationOfDependentClasses();
        System.out.println("recipe.toString() = " + recipe.toString());
        System.out.println("recipe.arrayLength() = " + recipe.arrayLength());
        model.addAttribute("color", AppConfig.getColor());
//        model.addAttribute("recipe", WorkWithDB.read(id));
        model.addAttribute("recipe", recipe);

        return DirtyJob.isMobile(userAgent) ? "recipe/editMobile" : "recipe/edit";
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
