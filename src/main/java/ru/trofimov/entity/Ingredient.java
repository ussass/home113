package ru.trofimov.entity;

public class Ingredient {
    private String ingredientName;
    private int quantity;
    private int measure;

    public Ingredient(String ingredientName, int quantity, int measure) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measure = measure;
    }

    @Override
    public String toString() {
        return ingredientName + "&%&" + quantity + "&%&" + measure;
    }

    String show(){
        return ingredientName + " " + quantity + " " + measure;
    }
}
