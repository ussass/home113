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
            ing[i] = new Ingredient(x);
        }
        this.ingredients = ing;
        Step[] st = new Step[steps.split("&\\*&").length];
        for (int i = 0; i < st.length; i++){
            String x = steps.split("&\\*&")[i];
            st[i] = new Step(x);
        }
        this.steps = st;
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
        System.out.println(nameMainImage.toString());

        return "VALUES ('" + recipeName + "', " + category + ", " + portion + ", " + time + ", '" + ingredient.toString() + "', '" + step.toString() + "', '" + nameMainImage.toString() + "');";
//  "VALUES ('хлеб', 2, 2, 300, 'хлеб!!!2!!!2', 'St&dfdf-0.jpg!!!равловпагвпал', 'dfdf-0.jpg');");  " +  + "
//                          recipeName,           category,          portion,    cookingTime,        ingredients,                steps,                              photos

    }
}
