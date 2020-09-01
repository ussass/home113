package ru.trofimov.model;

public class DirtyJob {

    public static boolean isMobile (String userAgent){
        return userAgent.contains("Mobile");
    }
}
