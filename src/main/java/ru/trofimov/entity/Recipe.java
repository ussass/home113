package ru.trofimov.entity;

public class Recipe {

    private int id;
    private String recipeName;
    private int category;
    private int portion;
    private int time;
    private String[] namesMainImage;
    private Ingredient[] ingredients;
    private Step[] steps;

    public Recipe(int id, String recipeName, int category, int portion, int hour, int min, String[] NamesMainImage, Ingredient[] ingredients, Step[] steps) {
        this.id = id;
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.time = hour * 60 + min;
        this.namesMainImage = NamesMainImage;
        this.ingredients = ingredients;
        this.steps = steps;
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
        System.out.println("ingredients:");
        for (Ingredient x : ingredients) {
            System.out.print("   ");
            System.out.print(x.show() + " ");
        }
        System.out.println();
        System.out.println("steps:");
        for (Step x : steps) {
            System.out.print("   ");
            System.out.print(x.show() + " ");
        }
    }
}
