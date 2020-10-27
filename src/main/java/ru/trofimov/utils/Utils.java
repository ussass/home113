package ru.trofimov.utils;

public class Utils {
    public static String ArrayStringToString(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) builder.append(s).append("&*&");
        return builder.toString();
    }
}
