package ru.trofimov.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.model.Ingredient;
import ru.trofimov.model.Step;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CreateClassesForRecipe {

    public static Ingredient[] createIngredients(String[] ingredient, List<Integer> quantity, int[] measure) throws UnsupportedEncodingException {

        for (int i = 0; i < quantity.size(); i++){
            try{
                quantity.get(i).toString();
            }catch (NullPointerException e){
                quantity.set(i, 0);
            }
        }

        Ingredient[] ingredients = new Ingredient[ingredient.length];
        for (int i = 0; i < ingredients.length; i++){
            ingredients[i] = new Ingredient(Crutch.toUTF8(ingredient[i]), quantity.get(i), measure[i]);
        }
        return ingredients;
    }

    public static Step[] createSteps(MultipartFile[] files, String[] steps, String recipeName) throws UnsupportedEncodingException {
        ArrayList<String> list = new ArrayList<>();
        for (String step : steps) {
            if (!step.equals("")) list.add(Crutch.toUTF8(step));
        }
        Step[] stepsArray = new Step[list.size()];
        WorkWithMultipartFile work = new WorkWithMultipartFile(files, recipeName);
        String[] stepsImage = work.saveFiles(true);
        for (int i = 0; i < stepsArray.length; i++){
            stepsArray[i] = new Step(list.get(i), stepsImage[i]);
        }
        return stepsArray;
    }

    public static Step[] createSteps(String[] steps, String[] photoSteps) throws UnsupportedEncodingException {
        Step[] stepsArray = new Step[steps.length];
        for (int i = 0; i < stepsArray.length; i++){
            stepsArray[i] = new Step(Crutch.toUTF8(steps[i]), photoSteps[i]);
        }
        return stepsArray;
    }

    public static String createIngredientsString(String[] ingredient, List<Integer> quantity, int[] measure) throws UnsupportedEncodingException {
        Ingredient[] ingredients = createIngredients(ingredient, quantity, measure);
        StringBuilder builder = new StringBuilder();
        for (Ingredient value : ingredients) {
            builder.append(value.getIngredientName()).append("&%&");
            builder.append(value.getQuantity()).append("&%&");
            builder.append(value.getMeasure()).append("&*&");
        }
        return builder.toString();
    }

    public static String createStepsString(MultipartFile[] files, String[] steps, String recipeName) throws UnsupportedEncodingException {
        return stepArrayToString(createSteps(files, steps, recipeName));
    }

    public static String createStepsString (String[] steps, String[] photoSteps) throws UnsupportedEncodingException {
        return stepArrayToString(createSteps(steps, photoSteps));
    }

    private static String stepArrayToString(Step[] array){
        StringBuilder builder = new StringBuilder();
        for (Step value : array) {
            builder.append(value.getDescription()).append("&%&");
            builder.append(value.getPathToImage()).append("&*&");
        }
        return builder.toString();
    }

}
