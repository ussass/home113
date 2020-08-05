package ru.trofimov.model;

import ru.trofimov.entity.Ingredient;

import java.io.UnsupportedEncodingException;

public class CreateClassesForRecipe {

    public static Ingredient[] createIngredients(String[] ingredient, int[] quantity, int[] measure) throws UnsupportedEncodingException {
        Ingredient[] ingredients = new Ingredient[ingredient.length];
        for (int i = 0; i < ingredients.length; i++){
            ingredients[i] = new Ingredient(Crutch.toUTF8(ingredient[i]), quantity[i], measure[i]);
        }
        return ingredients;
    }


}
