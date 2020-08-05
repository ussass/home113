package ru.trofimov.model;

import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.entity.Ingredient;
import ru.trofimov.entity.Step;

import java.io.UnsupportedEncodingException;

public class CreateClassesForRecipe {

    public static Ingredient[] createIngredients(String[] ingredient, int[] quantity, int[] measure) throws UnsupportedEncodingException {
        Ingredient[] ingredients = new Ingredient[ingredient.length];
        for (int i = 0; i < ingredients.length; i++){
            ingredients[i] = new Ingredient(Crutch.toUTF8(ingredient[i]), quantity[i], measure[i]);
        }
        return ingredients;
    }

    public static Step[] createSteps(MultipartFile[] files, String[] steps, String recipeName) throws UnsupportedEncodingException {
        Step[] stepsArray = new Step[steps.length];
        WorkWithMultipartFile work = new WorkWithMultipartFile(files, recipeName);
        String[] stepsImage = work.saveFiles(true);
        for (String x : stepsImage){
            System.out.println(x + "         CreateClassesForRecipe:24");
        }
        for (int i = 0; i < stepsArray.length; i++){
            stepsArray[i] = new Step(Crutch.toUTF8(steps[i]), stepsImage[i]);
        }
        return stepsArray;
    }
}
