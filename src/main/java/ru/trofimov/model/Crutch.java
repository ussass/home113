package ru.trofimov.model;

import com.ibm.icu.text.Transliterator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Crutch {

    public static String toUTF8(String string) throws UnsupportedEncodingException {
        return new String(string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public static String toTranscript(String ruText){
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        return toLatinTrans.transliterate(ruText).replaceAll(" ", "-");
    }

    public static int categoryStringToInt (String category){
        switch (category){
            case "breakfast": return 1;
            case "soups": return 2;
            case "main-dishes": return 3;
            case "baking-and-Desserts": return 4;
            case "sandwiches": return 5;
            case "pasta-and-Pizza": return 6;
            case "salads": return 7;
            case "sauces": return 8;
            case "beverages": return 9;
            default: return  0;
        }
    }

    public static void recursiveDelete(File file) {
        // до конца рекурсивного цикла
        if (!file.exists())
            return;

        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // рекурсивный вызов
                recursiveDelete(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
    }
}
