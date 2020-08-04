package ru.trofimov.entity;

public class Recipe {

    private int id;
    private String recipeName;
    private int category;
    private int portion;
    private int time;
    private String[] pathToMainImage;
    private Ingredient[] ingredients;
    private Step[] steps;

    public Recipe(int id, String recipeName, int category, int portion, int hour, int min, String[] pathToMainImage, Ingredient[] ingredients, Step[] steps) {
        this.id = id;
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.time = hour * 60 + min;
        this.pathToMainImage = pathToMainImage;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(String recipeName, int category, int portion, int hour, int min) {
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.time = hour * 60 + min;
    }

    public void showFields(){
        System.out.println("id: " + id);
        System.out.println("recipeName: " + recipeName);
        System.out.println("category: " + category);
        System.out.println("portion: " + portion);
        System.out.println("time: " + time);
    }
}
