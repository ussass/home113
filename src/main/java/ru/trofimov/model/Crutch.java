package ru.trofimov.model;

import com.ibm.icu.text.Transliterator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static void removeImage(String oldImage, MultipartFile[] newImage, int[] delMainPhoto){
        String[] oldImagNames = oldImage.split("!%!");
        if (oldImagNames.length == 0) return;
        System.out.println("------ Crutch.removeImage ------");

        int count = newImage.length;
        if (Objects.equals(newImage[0].getResource().getFilename(), "")) count = 0;
        System.out.println(count);

        System.out.println(oldImage);
        String[] split1 = oldImagNames[0].split("-");

        String oldRecipeName = "";

        for (int i = 0; i < split1.length - 1; i++){
            oldRecipeName += split1[i];
        }

        List<String> list = new ArrayList<>();
        List<String> listDel = new ArrayList<>();

        for (int i = 0; i < oldImagNames.length; i++){
            if (delMainPhoto[i] == 0) list.add(oldImagNames[i]);
            else listDel.add(oldImagNames[i]);
        }

        for (String x : list)
            System.out.println("move image: " + x);
        for (String x : listDel)
            System.out.println(" del image: " + x);





        System.out.println("--------------------------------");
    }
}
