package ru.trofimov.entity;

public class Step {

    private String description;
    private String pathToImage;

    public Step(String description, String pathToImage) {
        this.description = description;
        this.pathToImage = pathToImage;
    }

    String show(){
        return description + " " + pathToImage;
    }
}
