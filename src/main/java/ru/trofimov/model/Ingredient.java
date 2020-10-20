package ru.trofimov.model;

public class Ingredient {
    private String ingredientName;
    private int quantity;
    private int measure;

    public Ingredient(String ingredientName, int quantity, int measure) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measure = measure;
    }

    public Ingredient(String allInOne) {
        this.ingredientName = allInOne.split("&%&")[0];
        this.quantity = Integer.parseInt(allInOne.split("&%&")[1]);
        this.measure = Integer.parseInt(allInOne.split("&%&")[2]);
    }

    @Override
    public String toString() {
        return ingredientName + "&%&" + quantity + "&%&" + measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        switch (measure){
            case 0: return "грамм";
            case 1: return "килограмм";
            case 2: return "миллилитр";
            case 3: return "литр";
            case 4: return "по вкусу";
            case 5: return "на кончике ножа";
            case 6: return "защепотка";
            case 7: return "чайная ложка";
            case 8: return "столовая ложка";
            case 9: return "стакан";
            case 10: return "головка";
            case 11: return "штука";
            case 12: return "кусок";
            default: return "Нет данных";
        }
    }

    public int getMeasureInt(){
        return measure;
    }

    public String show(){
        return ingredientName + " " + quantity + " " + measure;
    }
}
