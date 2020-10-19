package ru.trofimov.model;

import ru.trofimov.entity.Ingredient;
import ru.trofimov.entity.Step;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String recipeName;

    private int category;

    private int portion;

    private int cookingTime;

    @Column(name = "ingredients")
    private String ingredientsString;

    @Column(name = "steps")
    private String stepsString;

    @Column(name = "photos")
    private String photosString;

    @Transient
    private Ingredient[] ingredients;

    @Transient
    private Step[] steps;

    public Recipe() {
    }

    public void initializationOfDependentClasses(){
        Ingredient[] ing = new Ingredient[ingredientsString.split("&\\*&").length];
        for (int i = 0; i < ing.length; i++) {
            String x = ingredientsString.split("&\\*&")[i];
            if (!x.equals("")) ing[i] = new Ingredient(x);
        }
        this.ingredients = ing;
        Step[] st = new Step[stepsString.split("&\\*&").length];
        for (int i = 0; i < st.length; i++) {
            String x = stepsString.split("&\\*&")[i];
            if (!x.equals("")) st[i] = new Step(x);
        }
        this.steps = st;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getIngredientsString() {
        return ingredientsString;
    }

    public void setIngredientsString(String ingredientsString) {
        this.ingredientsString = ingredientsString;
    }

    public String getStepsString() {
        return stepsString;
    }

    public void setStepsString(String stepsString) {
        this.stepsString = stepsString;
    }

    public String getPhotosString() {
        return photosString;
    }

    public void setPhotosString(String photosString) {
        this.photosString = photosString;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }
}
