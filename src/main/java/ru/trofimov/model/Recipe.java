package ru.trofimov.model;

import javax.persistence.*;
import java.util.Date;

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

    private int datecreate;

    @Transient
    private Ingredient[] ingredients;

    @Transient
    private Step[] steps;

    @Transient
    private String[] namesMainImage;

    public Recipe() {
    }

    public Recipe(String recipeName, int category, int portion, int hour, int min, String photosString, String ingredientsString, String stepsString) {
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.cookingTime = hour * 60 + min;
        this.photosString = photosString;
        this.ingredientsString = ingredientsString;
        this.stepsString = stepsString;
        this.datecreate = (int)(new Date().getTime()/1000/60);
    }

    public void initializationOfDependentClasses(){
        if (ingredientsString.equals("")) ingredientsString = "&%&0&%&0&*&";
        Ingredient[] ing = new Ingredient[ingredientsString.split("&\\*&").length];
        for (int i = 0; i < ing.length; i++) {
            String x = ingredientsString.split("&\\*&")[i];
            if (!x.equals("")) ing[i] = new Ingredient(x);
        }
        this.ingredients = ing;
        if (stepsString.equals("")) stepsString = "&%&0&*&";
        Step[] st = new Step[stepsString.split("&\\*&").length];
        for (int i = 0; i < st.length; i++) {
            String x = stepsString.split("&\\*&")[i];
            if (!x.equals("")) st[i] = new Step(x);
        }
        this.steps = st;
        if (photosString.equals("")) namesMainImage = new String[]{""};
        else namesMainImage = photosString.split("&\\*&");
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

    public String[] getNamesMainImage() {
        return namesMainImage;
    }

//    -------------------- Лишние? --------------------

    public String getCategoryString(){
        return DirtyJob.intCategoryToString(category);
    }

    public String getLinkWithId() {
        return Crutch.toTranscript(recipeName) + "-" + id;
    }

    public String getMainImage() {
        return photosString.split("&\\*&")[0];
    }

    public int getHour() {
        return cookingTime / 60;
    }

    public int getMinute() {
        return cookingTime % 60;
    }

    public String getTime() {
        int hour, min;
        min = cookingTime % 60;
        hour = (cookingTime - min) / 60;
        if (hour == 0 && min == 0) return "---";
        else if (hour > 0 && min == 0) return hour + correctHour(hour);
        else if (hour == 0 && min > 0) return min + " минут";
        else return hour + correctHour(hour) + min + " минут";
    }

    private String correctHour(int hour) {
        int lastN = hour % 10;
        int penultN = (hour % 100 - lastN) / 10;
        if (lastN == 1 && penultN != 1) return " час ";
        if ((lastN == 2 || lastN == 3 || lastN == 4) && penultN != 1) return " часа ";
        else return " часов ";
    }

    public String getNamesMainImageOneLine() {
        StringBuilder sb = new StringBuilder();
        for (String x : namesMainImage)
            sb.append(x).append("!%!");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "\nid=" + id +
                ", \nrecipeName='" + recipeName + '\'' +
                ", \ncategory=" + category +
                ", \nportion=" + portion +
                ", \ncookingTime=" + cookingTime +
                ", \ningredientsString='" + ingredientsString + '\'' +
                ", \nstepsString='" + stepsString + '\'' +
                ", \nphotosString='" + photosString + '\'' +
                "\n}";
    }

    public String arrayLength(){
//        ingredients = new Ingredient[0];
//        steps = new Step[0];
//        namesMainImage = new String[0];
        return "ingredients.length: " + ingredients.length +
                ", \nsteps.length: " + steps.length +
                ", \nnamesMainImage.length: " + namesMainImage.length +
                "";
    }
}
