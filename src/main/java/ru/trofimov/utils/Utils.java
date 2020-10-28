package ru.trofimov.utils;

public class Utils {
    public static String ArrayStringToString(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) builder.append(s).append("&*&");
        return builder.toString();
    }

    public static boolean isMobile (String userAgent){
        return userAgent.contains("Mobile");
    }

    public static String intCategoryToString(int intCategory){
        switch (intCategory) {
            case 1:
                return "Завтраки";
            case 2:
                return "Супы";
            case 3:
                return "Основные блюда";
            case 4:
                return "Выпечка и десерты";
            case 5:
                return "Сэндвичи";
            case 6:
                return "Паста и пицца";
            case 7:
                return "Салаты";
            case 8:
                return "Соусы";
            case 9:
                return "Напитки";
            default:
                return "Все рецепты";
        }
    }
}
