package ru.trofimov.model;

import ru.trofimov.entity.ImgDescription;

import java.util.ArrayList;
import java.util.List;

public class MyFile {
    private static MyFile instance = new MyFile();

    private List<ImgDescription> imgList;

    public static MyFile getInstance() {
        return instance;
    }

    private MyFile() {
        imgList = new ArrayList<>();
    }

    public void add(ImgDescription user) {
        imgList.add(user);
    }

    public List<ImgDescription> list() {
        return imgList;
    }
}

