package ru.trofimov.model;

public class Step {

    private String description;
    private String pathToImage;

    public Step(String description, String pathToImage) {
        this.description = description;
        this.pathToImage = pathToImage;
    }

    public Step(String allInOne) {
        this.description = allInOne.split("&%&")[0];
        this.pathToImage = allInOne.split("&%&")[1];
    }

    public String getDescription() {
        return description;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public String getPathToImageOrEmpty() {
        if(pathToImage.contains("octet-stream") || pathToImage.equals("0"))
            return "";

        return pathToImage;
    }

    @Override
    public String toString() {
        return description + "&%&" + pathToImage;
    }

    public String show() {
        return description + " " + pathToImage;
    }
}
