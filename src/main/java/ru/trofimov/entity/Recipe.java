package ru.trofimov.entity;


import ru.trofimov.model.Crutch;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String recipeName;
    private int category;
    private int portion;
    private int time;
    private String[] namesMainImage;
    private Ingredient[] ingredients;
    private Step[] steps;

    public Recipe(String recipeName, int category, int portion, int hour, int min, String[] NamesMainImage, Ingredient[] ingredients, Step[] steps) {
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.time = hour * 60 + min;
        this.namesMainImage = NamesMainImage;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(int id, String recipeName, int category, int portion, int time, String namesMainImage, String ingredients, String steps) {
        this.id = id;
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.time = time;
        this.namesMainImage = namesMainImage.split("&\\*&");
        Ingredient[] ing = new Ingredient[ingredients.split("&\\*&").length];
        for (int i = 0; i < ing.length; i++){
            String x = ingredients.split("&\\*&")[i];
            if (!x.equals("")) ing[i] = new Ingredient(x);
        }
        this.ingredients = ing;
        Step[] st = new Step[steps.split("&\\*&").length];
        for (int i = 0; i < st.length; i++){
            String x = steps.split("&\\*&")[i];
            if (!x.equals("")) st[i] = new Step(x);
        }
        this.steps = st;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getTime() {
        int hour, min;
        min = time % 60;
        hour = (time - min) / 60;
        if (hour == 0 && min == 0) return "---";
        else if (hour > 0 && min == 0) return hour + correctHour(hour);
        else if (hour == 0 && min > 0) return min + " минут";
        else return hour + correctHour(hour) + min + " минут";

    }

    public String getLinkWithId(){
        return Crutch.toTranscript(recipeName) + "-" + id;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        switch (category){
            case 1: return "Завтраки";
            case 2: return "Супы";
            case 3: return "Основные блюда";
            case 4: return "Выпечка и десерты";
            case 5: return "Сэндвичи";
            case 6: return "Паста и пицца";
            case 7: return "Салаты";
            case 8: return "Соусы";
            case 9: return "Напитки";
            default: return "Нет данных";
        }
    }

    public int getPortion() {
        return portion;
    }

    public String[] getNamesMainImage() {
        return namesMainImage;
    }

    public Ingredient[] getIngredients() {
        try{
            ingredients[0].toString();
            return ingredients;
        }catch (Exception e){
            return new Ingredient[] {new Ingredient("",0,0)};
        }
    }

    public Step[] getSteps() {
        return steps;
    }

    public void showFields(){
        System.out.println("id: " + id);
        System.out.println("recipeName: " + recipeName);
        System.out.println("category: " + category);
        System.out.println("portion: " + portion);
        System.out.println("time: " + time);
        System.out.println("namesMainImage:");
        for (String x : namesMainImage) {
            System.out.print("   ");
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.println("ingredients: " + ingredients.length);
        for (Ingredient x : ingredients) {
            System.out.print("   ");
//            System.out.print(x.show() + " ");
        }
        System.out.println();
        System.out.println("steps:");
        for (Step x : steps) {
            System.out.print("   ");
//            System.out.print(x.show() + " ");
        }
        System.out.println();
        System.out.println("-------------------");
    }

    public String insertIntoDb(){
        StringBuilder ingredient = new StringBuilder();
        for (Ingredient value : ingredients) {
            ingredient.append(value.toString()).append("&*&");
        }

        StringBuilder step = new StringBuilder();
        for (Step value : steps) {
            step.append(value.toString()).append("&*&");
        }

        StringBuilder nameMainImage = new StringBuilder();
        for (String value : namesMainImage) {
            nameMainImage.append(value).append("&*&");
        }
        System.out.println("nameMainImage.toString: " + nameMainImage.toString());

        return "VALUES ('" + recipeName + "', " + category + ", " + portion + ", " + time + ", '" + ingredient.toString() + "', '" + step.toString() + "', '" + nameMainImage.toString() + "');";

    }

    private String correctHour(int hour){
        int lastN = hour % 10;
        int penultN = (hour % 100 - lastN) / 10;
        if (lastN == 1 && penultN != 1) return " час ";
        if ((lastN == 2 || lastN == 3 || lastN == 4) && penultN != 1) return " часа ";
        else return " часов ";
    }
}
