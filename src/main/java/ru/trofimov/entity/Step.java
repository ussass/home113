package ru.trofimov.entity;

public class Step {

    private String description;
    private String pathToImage;

    public Step(String description, String pathToImage) {
        this.description = description;
        this.pathToImage = pathToImage;
    }

    @Override
    public String toString() {
        return description + "&%&" + pathToImage;
    }

    String show(){
        return description + " " + pathToImage;
    }
}
